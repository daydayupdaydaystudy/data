package com.car.data.schedules;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.*;
import com.alibaba.fastjson.JSONObject;
import com.car.data.dao.*;
import com.car.data.entity.*;
import com.car.data.enums.NoticeEnum;
import com.car.data.utils.GlobalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class NoticeContentScheduler {
    @Autowired
    NoticeInfoDao noticeInfoDao;
    @Autowired
    NoticeContentDao noticeContentDao;
    @Autowired
    PersonRecordDao personRecordDao;
    @Autowired
    PersonApplyInfoDao personApplyInfoDao;
    @Autowired
    CompanyRecordDao companyRecordDao;
    @Autowired
    CompanyApplyInfoDao companyApplyInfoDao;

    @Transactional
//    @Scheduled(cron = "4/5 * * * * ?")
    @Scheduled(cron = "0/5 0 18 26-31 * ?")
    public void execute() throws IOException {
        NoticeInfo noticeInfo = noticeInfoDao.findFirst();
        if (noticeInfo == null)
            return;
        log.info("【定时任务】解析公告通知：" + JSONObject.toJSONString(noticeInfo));
        long begin = System.currentTimeMillis();
        if (NoticeEnum.Notice_1.getCode().equals(noticeInfo.getType())) {
            //摇号结果公告
            lotteryResults(noticeInfo);
        } else if (NoticeEnum.Notice_2.getCode().equals(noticeInfo.getType())) {
            //竞价情况
        } else if (NoticeEnum.Notice_3.getCode().equals(noticeInfo.getType())) {
            //摇号公告
        } else if (NoticeEnum.Notice_4.getCode().equals(noticeInfo.getType())) {
            //竞价公告
        } else if (NoticeEnum.Notice_5.getCode().equals(noticeInfo.getType())) {
            //配置数量公告
        }
        noticeInfoDao.used(noticeInfo.getId());
        log.info("【定时任务】解析公告通知结束，耗时：" + (System.currentTimeMillis() - begin));
    }

    /**
     * 获取摇号结果文件，并解析文件数据
     *
     * @param noticeInfo
     */
    private void lotteryResults(NoticeInfo noticeInfo) throws IOException {
        //根据url获取解析后的数据
        String[] strArr = getDataByUrl(noticeInfo.getHref());
        NoticeContent noticeContent = createNoticeContent(strArr);
        noticeContent.setName(noticeInfo.getName());
        noticeContent.setIssueNum(GlobalUtil.getIssuenum(noticeInfo.getName()));
        log.info("【解析公告通知】页面数据：" + JSONObject.toJSONString(noticeContent));
        //根据文件地址下载文件
        String prefix = "file/" + noticeContent.getIssueNum() + '/';
        File person = new File(prefix + noticeContent.getPersonFileName());
        HttpUtil.downloadFile(noticeContent.getPersonFileUrl(), person);
        String text = GlobalUtil.readPdfText(person);
        PersonRecord personRecord = GlobalUtil.getPersonRecord(text);
        log.info("【解析公告通知】个人-文件数据：" + JSONObject.toJSONString(personRecord));
        List<PersonApplyInfo> personApplyInfos = GlobalUtil.getPersonApplyInfo(text, personRecord.getIssueNum());
        log.info("【解析公告通知】个人-申请信息数量：" + personApplyInfos.size());

        //单位部分
        File company = new File(prefix + noticeContent.getCompanyFileName());
        HttpUtil.downloadFile(noticeContent.getCompanyFileUrl(), company);
        text = GlobalUtil.readPdfText(company);
        CompanyRecord companyRecord = GlobalUtil.getCompanyRecord(text);
        log.info("【解析公告通知】单位-文件数据：" + JSONObject.toJSONString(companyRecord));
        List<CompanyApplyInfo> companyApplyInfos = GlobalUtil.getCompanyApplyInfo(text, personRecord.getIssueNum());
        log.info("【解析公告通知】单位-申请信息数量：" + companyApplyInfos.size());
        noticeContentDao.save(noticeContent);
        personRecordDao.save(personRecord);
        personApplyInfoDao.insertBatch(personApplyInfos);
        companyRecordDao.save(companyRecord);
        companyApplyInfoDao.insertBatch(companyApplyInfos);
    }

    private NoticeContent createNoticeContent(String[] strArr) {
        int index = 0;
        NoticeContent noticeContent = new NoticeContent();
        noticeContent.setId(UUID.fastUUID().toString());
        noticeContent.setPersonPlanNum(strArr[index++]);
        noticeContent.setPersonApplyNum(strArr[index++]);
        noticeContent.setPersonActualNum(strArr[index++]);
        noticeContent.setCompanyPlanNum(strArr[index++]);
        noticeContent.setCompanyApplyNum(strArr[index++]);
        noticeContent.setCompanyActualNum(strArr[index++]);
        noticeContent.setSumPlanNum(strArr[index++]);
        noticeContent.setSumApplyNum(strArr[index++]);
        noticeContent.setSumActualNum(strArr[index++]);
        noticeContent.setPersonFileUrl(strArr[index++]);
        noticeContent.setPersonFileName(strArr[index++]);
        noticeContent.setCompanyFileUrl(strArr[index++]);
        noticeContent.setCompanyFileName(strArr[index]);
        return noticeContent;
    }

    private String[] getDataByUrl(String href) {
        HttpRequest request = HttpUtil.createGet(href);
        HttpResponse response = request.execute();
        if (!response.isOk()) {
            throw new HttpException("地址请求失败：" + href);
        }
        String body = response.body();
        List<String> p = GlobalUtil.findAll(body, true, "p");
        String[] strArr = new String[13];
        int index = 0;
        for (String s : p) {
            if (s.contains("特此公告") || s.contains("中签") || s.contains("数")
                    || s.contains("中心")  || s.contains("年")
                    || s.contains("指标计划") || s.contains("有效申请")|| s.contains("实际配置")
                    || s.contains("指标分类")|| s.contains("情况见下表")) {
                continue;
            }
            if (!s.contains("span")) {
                continue;
            }
            if (s.contains("附件") && !s.contains("href"))
                continue;
            if (s.contains("MD5") && !s.contains("指标配置结果"))
                continue;
            if (s.contains("小汽车") && !s.contains("href"))
                continue;
            if (s.contains("普通") && !s.contains("href"))
                continue;
            s = s.replaceAll("&nbsp;", "");
            if (s.contains("href") && s.contains("指标配置结果")) {
                if (s.contains("电动")){
                    continue;
                }
                s = HtmlUtil.removeHtmlAttr(s, "ATTACHID");
                Document document;
                try{
                    document = XmlUtil.parseXml(s);
                } catch (Exception e) {
                    continue;
                }
                Element rootElement = XmlUtil.getRootElement(document);
                if (StringUtils.isBlank(rootElement.getTextContent())){
                    continue;
                }
                Element a = XmlUtil.getElement(rootElement, "a");
                if (a == null) {
                    String format = XmlUtil.format(document);
                    List<String> a1 = GlobalUtil.findAll(format, true, "a");
                    if (a1 != null && a1.size() > 0) {
                        a = XmlUtil.getRootElement(XmlUtil.parseXml(a1.get(0)));
                    }
                }
//                if (a == null){
//                    Element span = XmlUtil.getElement(rootElement, "span");
//                    a = XmlUtil.getElement(span, "a");
//                    if (a == null) {
//                        Element span_2 = XmlUtil.getElement(span, "span");
//                        a = XmlUtil.getElement(span_2, "a");
//                        if (a==null) {
//                            Node lastChild = span.getLastChild();
//                            a = XmlUtil.getElement((Element) lastChild,"a");
//                        }
//                    }
//                }
                String attr = a.getAttribute("href");
                strArr[index++] = attr;
                String[] split = attr.split("\\/");
                strArr[index++] = split[split.length - 1];
            } else {
                if (index > 8)
                    continue;
                if (s.contains("普通小汽车") || s.contains("个人") || s.contains("单位")|| s.contains("合计")) {
                    continue;
                }
                Document document;
                try{
                    document = XmlUtil.parseXml(s);
                } catch (Exception e) {
                    continue;
                }
                Element rootElement = XmlUtil.getRootElement(document);
                String textContent = rootElement.getTextContent();
                if (StringUtils.isBlank(textContent)) {
                    continue;
                }
                strArr[index++] = textContent;
            }
        }
        return strArr;
    }
}
