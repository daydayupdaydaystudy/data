//package com.car.data;
//
//import cn.hutool.core.lang.UUID;
//import cn.hutool.core.util.ReUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.core.util.XmlUtil;
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import com.car.data.config.CarConfig;
//import com.car.data.dao.IssueInfoDao;
//import com.car.data.dao.LatestNoticeDao;
//import com.car.data.entity.LatestNotice;
//import com.car.data.schedules.ApplyInfoScheduler;
//import com.car.data.schedules.NoticeInfoScheduler;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.apache.pdfbox.text.PDFTextStripperByArea;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.w3c.dom.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
//@SpringBootTest
//class DataApplicationTests {
//    @Autowired
//    CarConfig carConfig;
//    @Autowired
//    ApplyInfoScheduler applyInfoScheduler;
//    @Autowired
//    IssueInfoDao issueInfoDao;
//    @Autowired
//    LatestNoticeDao latestNoticeDao;
//    @Autowired
//    NoticeInfoScheduler noticeInfoScheduler;
//
//    @Test
//    public void testnotice(){
//        noticeInfoScheduler.execute();
//    }
//
//    @Test
//    public void testcar(){
////        applyInfoScheduler.getApplyInfos();
//        String s = "      -中签详细列表数据-";
//        System.out.println(s.trim());
//    }
//
//    @Test
//    void testlist(){
//        HttpRequest request = HttpUtil.createGet("http://xqctk.jtys.sz.gov.cn/gbl/index.html");
//        HttpResponse response = request.execute();
//        String body = response.body();
////        System.out.println(body);
//        List<String> dl = findAll(body, true, "dl");
//        for (String s : dl) {
//            if (s.contains("place"))
//                continue;
//            System.out.println(s);
//            Document document = XmlUtil.parseXml(s);
//            Element rootElement = XmlUtil.getRootElement(document);
//            List<Element> dd = XmlUtil.getElements(rootElement, "dd");
//            for (Element element : dd) {
//                Element a = XmlUtil.getElement(element, "a");
//                String href = a.getAttribute("href");
//                System.out.println(href);
//                String name = a.getTextContent();
//                System.out.println(name);
//                Element span = XmlUtil.getElement(element, "span");
//                String date = span.getTextContent();
//                System.out.println(date);
//            }
//        }
//    }
//
//    @Test
//    void contextLoads() throws IOException {
//    }
//
//
//    public static List<String> findAll(String content, boolean withTagContent, String... tagNames) {
//        String[] var4 = tagNames;
//        int var5 = tagNames.length;
//        List<String> all = null;
//        for(int var6 = 0; var6 < var5; ++var6) {
//            String tagName = var4[var6];
//            if (!StrUtil.isBlank(tagName)) {
//                tagName = tagName.trim();
//                String regex;
//                if (withTagContent) {
//                    regex = StrUtil.format("(?i)<{}\\s*?[^>]*?/?>(.*?</{}>)?", new Object[]{tagName, tagName});
//                } else {
//                    regex = StrUtil.format("(?i)<{}\\s*?[^>]*?>|</{}>", new Object[]{tagName, tagName});
//                }
//                all = ReUtil.findAll(regex, content, 0);
//            }
//        }
//        return all;
//    }
//
//    @Test
//    public void testbreak(){
//        LatestNotice latestNotice = new LatestNotice();
//        latestNotice.setId(UUID.fastUUID().toString());
//        latestNotice.setName("深圳市小汽车指标网上申请开通");
//        latestNoticeDao.updateInstance(latestNotice);
////        latestNotice.setName("name2");
////        latestNoticeDao.updateInstance(latestNotice);
//    }
//
//}
