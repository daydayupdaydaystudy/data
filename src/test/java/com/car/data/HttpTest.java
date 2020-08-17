//package com.car.data;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.ReUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.core.util.XmlUtil;
//import cn.hutool.http.HtmlUtil;
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import com.car.data.schedules.NoticeContentScheduler;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootTest
//public class HttpTest {
//    @Autowired
//    NoticeContentScheduler noticeContentScheduler;
//
//    @Test
//    public void testschedule() throws IOException {
//        noticeContentScheduler.execute();
//    }
//
//    @Test
//    public void test() {
//        String href = "http://xqctk.jtys.sz.gov.cn/gbl/20200525/1590395434849_1.html";
//        HttpRequest request = HttpUtil.createGet(href);
//        HttpResponse response = request.execute();
//        if (!response.isOk()) {
//            return;
//        }
//        String body = response.body();
//        List<String> table = findAll(body, true, "table");
//        for (String s : table) {
//            s = s.replaceAll("&nbsp;", "");
//            s = XmlUtil.format(s);
//            Document document = XmlUtil.parseXml(s);
//            Element rootElement = XmlUtil.getRootElement(document);
//            Element tr = XmlUtil.getElement(rootElement, "tr");
//            String textContent = tr.getTextContent().trim();
//            String[] textArr = textContent.split(" ");
//            List<String> list = new ArrayList<>();
//            for (int i = 0; i < textArr.length; i++) {
//                if (StringUtils.isNotBlank(textArr[i])) {
//                    if (textArr[i].contains("竞买人") || textArr[i].contains("配置增量指标")) {
//                        list.add(textArr[i].trim());
//                    }
//                }
//            }
//            String content = list.get(0);
//            String[] split = content.split("\\p{P}");
//            for (String s1 : split) {
//                if (s1.contains("个")){
//                    System.out.println(s1);
//                    if (s1.contains("本期以竞价方式向个人和单位配置增量指标共")){
//                        String replace = s1.replace("本期以竞价方式向个人和单位配置增量指标共", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                    if (s1.contains("其中个人增量指标")){
//                        String replace = s1.replace("其中个人增量指标", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                    if (s1.contains("单位增量指标")){
//                        String replace = s1.replace("单位增量指标", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                    if (s1.contains("通过资格审核的有效编码数个人为")){
//                        String replace = s1.replace("通过资格审核的有效编码数个人为", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                    if (s1.contains("单位为")){
//                        String replace = s1.replace("单位为", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                    if (s1.contains("本期参与竞价的有效编码数个人为")){
//                        String replace = s1.replace("本期参与竞价的有效编码数个人为", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                    if (s1.contains("成交编码数个人为")){
//                        String replace = s1.replace("成交编码数个人为", "").replace("个","");
//                        System.out.println("解析：" + replace);
//                    }
//                }
//            }
//            System.out.println();
//            content = list.get(1);
//            split = content.split("\\p{P}");
//            for (String s1 : split) {
//                if (s1.contains("元"))
//                    System.out.println(s1);
//            }
//            System.out.println();
//            content = list.get(2);
//            split = content.split("\\p{P}");
//            for (String s1 : split) {
//                if (s1.contains("元"))
//                    System.out.println(s1);
//            }
//        }
//    }
//
//    public static List<String> findAll(String content, boolean withTagContent, String... tagNames) {
//        String[] var4 = tagNames;
//        int var5 = tagNames.length;
//        List<String> all = null;
//        for (int var6 = 0; var6 < var5; ++var6) {
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
//    public void getIssuenum() {
//        String str = "深圳市2019年第1期普通小汽车增量指标摇号结果公告";
//        String[] split = str.split("期");
//        System.out.println(split[0]);
//        DateTime date = DateUtil.parse(split[0], "深圳市yyyy年第MM");
//        String format = DateUtil.format(date, "yyyyMM");
//        System.out.println(format);
//    }
//}
