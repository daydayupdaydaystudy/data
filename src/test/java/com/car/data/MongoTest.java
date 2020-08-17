//package com.car.data;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.io.unit.DataUnit;
//import cn.hutool.core.math.MathUtil;
//import cn.hutool.core.util.XmlUtil;
//import cn.hutool.http.*;
//import com.alibaba.fastjson.JSONObject;
//import com.car.data.entity.*;
//import com.car.data.utils.GlobalUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//
//import java.io.*;
//import java.math.BigDecimal;
//import java.util.*;
//
//
//@SpringBootTest
//public class MongoTest {
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Test
//    public void test() {
//        Query query = Query.query(Criteria.where("type").ne("1"));
//        Update update = new Update();
//        update.set("used", true);
//        mongoTemplate.updateMulti(query, update, NoticeInfo.class);
//    }
//
//    @Test
//    public void test1() throws IOException {
//        InputStream inputStream = new FileInputStream(new File("file/小汽车竞价情况表.xlsx"));
//        XSSFWorkbook excel = new XSSFWorkbook(inputStream);
//        XSSFSheet personSheet = excel.getSheetAt(0);
//        int lastRowNum = personSheet.getLastRowNum();
//        List<PersonBiddingResult> list = new ArrayList<>(lastRowNum);
//        for (int i = 3; i <= lastRowNum; i++) {
//            XSSFRow row = personSheet.getRow(i);
//            PersonBiddingResult biddingResult = new PersonBiddingResult();
//            biddingResult.setId(UUID.randomUUID().toString());
//            String issuenum = row.getCell(0).toString();
//            String format = DateUtil.format(DateUtil.parse(issuenum, "yyyy年第MM期"), "yyyyMM");
//            biddingResult.setIssueNum(format);
//            biddingResult.setDeployNum(row.getCell(1).toString().replace(".0", ""));
//            biddingResult.setActualNum(row.getCell(2).toString().replace(".0", ""));
//            biddingResult.setFirstAveragePrice(row.getCell(3).toString().replace(".0", ""));
//            biddingResult.setSecondAveragePrice(row.getCell(4).toString().replace(".0", ""));
//            biddingResult.setMinPrice(row.getCell(5).toString().replace(".0", ""));
//            biddingResult.setAveragePrice(row.getCell(6).toString().replace(".0", ""));
//            biddingResult.setMinPriceOfferNum(row.getCell(7).toString().replace(".0", ""));
//            biddingResult.setMinPriceDealNum(row.getCell(8).toString().replace(".0", ""));
//            biddingResult.setDealNUm(row.getCell(10).toString().replace(".0", ""));
//            biddingResult.setPayNum(row.getCell(11).toString().replace(".0", ""));
//            list.add(biddingResult);
//        }
//        for (PersonBiddingResult biddingResult : list) {
//            System.out.println(biddingResult.toString());
//        }
//        mongoTemplate.insertAll(list);
//    }
//
//    @Test
//    public void test3() {
//        String[] dataByUrl = getDataByUrl("http://xqctk.jtys.sz.gov.cn/gbl/20150727/1437989229474_1.html");
//        System.out.println(Arrays.toString(dataByUrl));
//    }
//
//    private String[] getDataByUrl(String href) {
//        HttpRequest request = HttpUtil.createGet(href);
//        HttpResponse response = request.execute();
//        if (!response.isOk()) {
//            throw new HttpException("地址请求失败：" + href);
//        }
//        String body = response.body();
//        List<String> p = GlobalUtil.findAll(body, true, "p");
//        String[] strArr = new String[13];
//        int index = 0;
//        for (String s : p) {
//            if (s.contains("特此公告") || s.contains("中签") || s.contains("数")
//                    || s.contains("中心") || s.contains("年")
//                    || s.contains("指标计划") || s.contains("有效申请") || s.contains("实际配置")
//                    || s.contains("指标分类") || s.contains("情况见下表")) {
//                continue;
//            }
//            if (!s.contains("span")) {
//                continue;
//            }
//            if (s.contains("附件") && !s.contains("href"))
//                continue;
//            if (s.contains("小汽车") && !s.contains("href"))
//                continue;
//            if (s.contains("普通") && !s.contains("href"))
//                continue;
//            if (s.contains("MD5") && !s.contains("指标配置结果"))
//                continue;
//            s = s.replaceAll("&nbsp;", "");
//            if (s.contains("href") && s.contains("指标配置结果")) {
//                if (s.contains("电动")) {
//                    continue;
//                }
//                s = HtmlUtil.removeHtmlAttr(s, "ATTACHID");
//                Document document;
//                try {
//                    document = XmlUtil.parseXml(s);
//                } catch (Exception e) {
//                    continue;
//                }
//                Element rootElement = XmlUtil.getRootElement(document);
//                if (StringUtils.isBlank(rootElement.getTextContent())) {
//                    continue;
//                }
//                Element a = XmlUtil.getElement(rootElement, "a");
////                try{
////                    if (a == null) {
////                        Element span = XmlUtil.getElement(rootElement, "span");
////                        a = XmlUtil.getElement(span, "a");
////                        if (a == null) {
////                            Element span_2 = XmlUtil.getElement(span, "span");
////                            a = XmlUtil.getElement(span_2, "a");
////                            if (a == null) {
////                                Node lastChild = span.getLastChild();
////                                a = XmlUtil.getElement((Element) lastChild, "a");
////                            }
////                        }
////                    }
////                } catch (Exception e) {
//////                    e.printStackTrace();
////                }
//                if (a == null) {
//                    String format = XmlUtil.format(document);
//                    List<String> a1 = GlobalUtil.findAll(format, true, "a");
//                    if (a1 != null && a1.size() > 0) {
//                        a = XmlUtil.getRootElement(XmlUtil.parseXml(a1.get(0)));
//                    }
//                }
//                String attr = a.getAttribute("href");
//                strArr[index++] = attr;
//                String[] split = attr.split("\\/");
//                System.out.println(s);
//                strArr[index++] = split[split.length - 1];
//            } else {
//                if (index > 8)
//                    continue;
//                if (s.contains("普通小汽车") || s.contains("个人") || s.contains("单位") || s.contains("合计")) {
//                    continue;
//                }
//                Document document;
//                try {
//                    document = XmlUtil.parseXml(s);
//                } catch (Exception e) {
//                    continue;
//                }
//                Element rootElement = XmlUtil.getRootElement(document);
//                String textContent = rootElement.getTextContent();
//                if (StringUtils.isBlank(textContent)) {
//                    continue;
//                }
//                System.out.println(s);
//                strArr[index++] = textContent;
//            }
//        }
//        return strArr;
//    }
//
//    @Test
//    public void test4() throws IOException {
//        String text = GlobalUtil.readPdfText(new File("file/201602/1456475420050.pdf"));
//        List<CompanyApplyInfo> companyApplyInfos = GlobalUtil.getCompanyApplyInfo(text, "201602");
//    }
//
//    @Test
//    public void test5() {
//        Query query = Query.query(Criteria.where("issueNum").is("201602"));
//        mongoTemplate.remove(query, CompanyRecord.class);
//    }
//
//    @Test
//    public void test6() {
//        List<CompanyRecord> all = mongoTemplate.findAll(CompanyRecord.class);
//        for (CompanyRecord personRecord : all) {
//            BigDecimal applyNum = new BigDecimal(personRecord.getApplyNum());
//            BigDecimal normNum = new BigDecimal(personRecord.getNormNum());
//            BigDecimal divide = normNum.divide(applyNum, 4, BigDecimal.ROUND_HALF_UP);
//            personRecord.setLotRate(divide.toString());
//            System.out.println(divide);
//        }
//        Query query = Query.query(Criteria.where("_id").ne(""));
//        mongoTemplate.remove(query, CompanyRecord.class);
//        mongoTemplate.insertAll(all);
//    }
//
//    @Test
//    public void test7() {
//        List<PersonApplyInfo> all = mongoTemplate.findAll(PersonApplyInfo.class);
//        System.out.println(all.size());
//        HashMap<String, Integer> map = new HashMap<>();
//        for (PersonApplyInfo personApplyInfo : all) {
//            String applyNo = personApplyInfo.getApplyPrefix();
//            if (map.get(applyNo) == null) {
//                map.put(applyNo, 1);
//            } else {
//                map.replace(applyNo, map.get(applyNo) + 1);
//            }
//        }
//        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
//        list.sort(new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//        for (int i = 0; i < 100; i++) {
//            System.out.println(list.get(i));
//        }
//        System.out.println("===============");
//        for (int i = list.size() - 1; i >= list.size() - 50; i--) {
//            System.out.println(list.get(i));
//        }
//    }
//
//    @Test
//    public void test8() {
//        List<PersonRecord> all = mongoTemplate.findAll(PersonRecord.class);
//        HashMap<Integer, Integer> map = new HashMap<>();
//        for (PersonRecord personRecord : all) {
//            String initialValue = personRecord.getInitialValue();
//            char[] chars = initialValue.toCharArray();
//            for (char aChar : chars) {
//                int i = Integer.parseInt(String.valueOf(aChar));
//                map.merge(i, 1, Integer::sum);
//            }
//        }
//        System.out.println(map);
//    }
//
//    @Test
//    public void test9() {
//        List<VisitRecord> list = new ArrayList<>();
//        for (int i = 1; i < 32; i++) {
//            VisitRecord visitRecord = new VisitRecord();
//            visitRecord.setId(cn.hutool.core.lang.UUID.fastUUID().toString());
//            int random = (int) (Math.random() * 1000);
//            visitRecord.setCounts(String.valueOf(random));
//            Calendar instance = Calendar.getInstance();
//            instance.set(2020, Calendar.JULY, i);
//            Date time = instance.getTime();
//            visitRecord.setDate(DateUtil.format(time, "yyyy-MM-dd"));
//            System.out.println(JSONObject.toJSONString(visitRecord));
//            list.add(visitRecord);
//        }
//        mongoTemplate.insertAll(list);
//    }
//}
