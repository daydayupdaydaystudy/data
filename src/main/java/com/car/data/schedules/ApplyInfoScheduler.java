package com.car.data.schedules;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.car.data.config.CarConfig;
import com.car.data.dao.IssueInfoDao;
import com.car.data.dao.PersonApplyInfoDao;
import com.car.data.entity.IssueInfo;
import com.car.data.entity.PersonApplyInfo;
import com.car.data.utils.GlobalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人指标配置结果查询--定时任务
 */
@Slf4j
@Component
public class ApplyInfoScheduler {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CarConfig carConfig;

    @Autowired
    IssueInfoDao issueInfoDao;

    @Autowired
    PersonApplyInfoDao applyInfoDao;

//    @Scheduled(cron = "0 0 18 26-31 * ?")
    public void schedule(){
        log.info("[定时任务]查询个人中签信息开始");
        List<IssueInfo> list = issueInfoDao.findUseIssue();
        for (IssueInfo issueInfo : list) {
            getApplyInfos(issueInfo.getIssueNum());
            issueInfo.setUsed(true);
            issueInfoDao.used(issueInfo.getId());
        }
        log.info("[定时任务]查询个人中签信息结束");
    }

    private void getApplyInfos(String issueNumber) {
        HttpRequest request = HttpUtil.createPost(carConfig.applyInfoUrl);
        Map<String, Object> form = new HashMap<>();
        int pageNo = 1;
        form.put("issueNumber", issueNumber);
        form.put("applyCode", null);
        int count = 16;
        while (count == 16) {
            count = 0;
            form.put("pageNo", pageNo++);
            request.form(form);
            HttpResponse response = request.execute();
            String body = response.body();
            List<String> all = GlobalUtil.findAll(body, true, "tr");
            List<PersonApplyInfo> list = new ArrayList<>();
            for (String s : all) {
                if (s.contains("content_data")) {
                    count++;
                    Map<String, Object> map = XmlUtil.xmlToMap(s);
                    List<String> td = (ArrayList<String>) map.get("td");
                    String apply_no = td.get(0);
                    PersonApplyInfo applyInfo = new PersonApplyInfo();
                    applyInfo.setId(UUID.fastUUID().toString());
                    applyInfo.setName(td.get(1));
                    applyInfo.setApplyNo(apply_no);
                    applyInfo.setApplyPrefix(apply_no.substring(0, 4));
                    applyInfo.setApplyInfix(apply_no.substring(4, 8));
                    applyInfo.setApplyTail(apply_no.substring(8));
                    applyInfo.setIssue(issueNumber);
                    list.add(applyInfo);
                }
            }
            applyInfoDao.insertBatch(list);
        }
    }
}
