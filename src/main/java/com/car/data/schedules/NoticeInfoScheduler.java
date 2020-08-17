package com.car.data.schedules;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.car.data.config.CarConfig;
import com.car.data.dao.LatestNoticeDao;
import com.car.data.dao.NoticeInfoDao;
import com.car.data.entity.LatestNotice;
import com.car.data.entity.NoticeInfo;
import com.car.data.enums.NoticeEnum;
import com.car.data.utils.GlobalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class NoticeInfoScheduler {

    @Autowired
    CarConfig carConfig;
    @Autowired
    LatestNoticeDao latestNoticeDao;
    @Autowired
    NoticeInfoDao noticeInfoDao;

    @Scheduled(cron = "0 40 17 26-31 * ?")
    public void execute() {
        log.info("【定时任务】获取公告列表开始");
        LatestNotice latestNotice = latestNoticeDao.getLastNoticeInstance();
        log.info("【获取公告列表】系统最新文件：" + JSONObject.toJSONString(latestNotice));
        int index = 1;
        boolean loop = true;
        boolean first = true;
        while (loop) {
            String tail = null;
            if (index == 1) {
                tail = "index.html";
            } else {
                tail = "index_" + index + ".html";
            }
            HttpRequest request = HttpUtil.createGet(carConfig.noticeInfoUrl + tail);
            HttpResponse response = request.execute();
            if (!response.isOk()) {
                break;
            }
            String body = response.body();
            List<String> dl = GlobalUtil.findAll(body, true, "dl");
            List<NoticeInfo> noticeInfos = new ArrayList<>();
            flag:
            for (String s : dl) {
                if (s.contains("place"))
                    continue;
                Document document = XmlUtil.parseXml(s);
                Element rootElement = XmlUtil.getRootElement(document);
                List<Element> dd = XmlUtil.getElements(rootElement, "dd");
                for (Element element : dd) {
                    Element a = XmlUtil.getElement(element, "a");
                    String name = a.getTextContent();
                    //判断如果这个文件之前已经扫描过，则停止扫描
                    if (name.equals(latestNotice.getName())) {
                        loop = false;
                        break flag;
                    }
                    NoticeInfo noticeInfo = new NoticeInfo();
                    noticeInfo.setId(UUID.fastUUID().toString());
                    noticeInfo.setName(name);
                    noticeInfo.setHref(a.getAttribute("href"));
                    Element span = XmlUtil.getElement(element, "span");
                    noticeInfo.setDate(span.getTextContent());
                    noticeInfo.setUsed(false);
                    if (NoticeEnum.getEnum(name) != null) {
                        noticeInfo.setType(NoticeEnum.getEnum(name).getCode());
                    }
                    noticeInfos.add(noticeInfo);
                    if (first) {
                        latestNotice.setName(name);
                        latestNoticeDao.updateInstance(latestNotice);
                        first = false;
                    }
                }
            }
            log.info("【获取公告列表】第"+ index +"页数据：" + JSONObject.toJSONString(noticeInfos));
            noticeInfoDao.insertBatch(noticeInfos);
            index++;
        }
        log.info("【定时任务】获取通知列表结束");
    }
}
