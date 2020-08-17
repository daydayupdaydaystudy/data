package com.car.data.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.car.data.entity.CompanyApplyInfo;
import com.car.data.entity.CompanyRecord;
import com.car.data.entity.PersonApplyInfo;
import com.car.data.entity.PersonRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalUtil {

    public static List<String> findAll(String content, boolean withTagContent, String... tagNames) {
        String[] var4 = tagNames;
        int var5 = tagNames.length;
        List<String> all = null;
        for(int var6 = 0; var6 < var5; ++var6) {
            String tagName = var4[var6];
            if (!StrUtil.isBlank(tagName)) {
                tagName = tagName.trim();
                String regex;
                if (withTagContent) {
                    regex = StrUtil.format("(?i)<{}\\s*?[^>]*?/?>(.*?</{}>)?", new Object[]{tagName, tagName});
                } else {
                    regex = StrUtil.format("(?i)<{}\\s*?[^>]*?>|</{}>", new Object[]{tagName, tagName});
                }
                all = ReUtil.findAll(regex, content, 0);
            }
        }
        return all;
    }

    public static String getIssuenum(String name) {
        String[] split = name.split("期");
        DateTime date = DateUtil.parse(split[0], "深圳市yyyy年第MM");
        return DateUtil.format(date, "yyyyMM");
    }

    public static String readPdfText(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        PDFTextStripper textStripper = new PDFTextStripper();
        String text = textStripper.getText(document);
        document.close();
        return text;
    }

    public static PersonRecord getPersonRecord(String text){
        PersonRecord personRecord = new PersonRecord();
        String lines[] = text.split("\\r?\\n");
        String[] pseron_record = new String[7];
        int pseron_record_index = 0;
        for (String line : lines) {
            line = line.trim();
            if (line.contains("：")){
                pseron_record[pseron_record_index++] = line.split("：")[1];
            }
        }
        pseron_record_index = 0;
        personRecord.setId(UUID.fastUUID().toString());
        personRecord.setIssueNum(pseron_record[pseron_record_index++]);
        personRecord.setDescription(pseron_record[pseron_record_index++]);
        personRecord.setDeployDate(pseron_record[pseron_record_index++]);
        personRecord.setDeployDateTime(pseron_record[pseron_record_index++]);
        personRecord.setApplyNum(pseron_record[pseron_record_index++]);
        personRecord.setNormNum(pseron_record[pseron_record_index++]);
        personRecord.setInitialValue(pseron_record[pseron_record_index]);
        BigDecimal applyNum = new BigDecimal(personRecord.getApplyNum());
        BigDecimal normNum = new BigDecimal(personRecord.getNormNum());
        BigDecimal divide = normNum.divide(applyNum, 4, BigDecimal.ROUND_HALF_UP);
        personRecord.setLotRate(divide.toString());
        return personRecord;
    }

    public static CompanyRecord getCompanyRecord(String text){
        CompanyRecord companyRecord = new CompanyRecord();
        String lines[] = text.split("\\r?\\n");
        String[] pseron_record = new String[7];
        int pseron_record_index = 0;
        for (String line : lines) {
            line = line.trim();
            if (line.contains("：") && !line.contains("简称")){
                pseron_record[pseron_record_index++] = line.split("：")[1];
            }
        }
        pseron_record_index = 0;
        companyRecord.setId(UUID.fastUUID().toString());
        companyRecord.setIssueNum(pseron_record[pseron_record_index++]);
        companyRecord.setDescription(pseron_record[pseron_record_index++]);
        companyRecord.setDeployDate(pseron_record[pseron_record_index++]);
        companyRecord.setDeployDateTime(pseron_record[pseron_record_index++]);
        companyRecord.setApplyNum(pseron_record[pseron_record_index++]);
        companyRecord.setNormNum(pseron_record[pseron_record_index++]);
        companyRecord.setInitialValue(pseron_record[pseron_record_index]);
        BigDecimal applyNum = new BigDecimal(companyRecord.getApplyNum());
        BigDecimal normNum = new BigDecimal(companyRecord.getNormNum());
        BigDecimal divide = normNum.divide(applyNum, 4, BigDecimal.ROUND_HALF_UP);
        companyRecord.setLotRate(divide.toString());
        return companyRecord;
    }

    public static List<PersonApplyInfo> getPersonApplyInfo(String text,String issueNumber) throws IOException {
        String lines[] = text.split("\\r?\\n");
        List<PersonApplyInfo> list = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();
            if (line.contains("：")){
                continue;
            } else if (line.startsWith("-")){
                continue;
            } else if (!line.contains(" ")){
                continue;
            } else if (line.startsWith("序号")){
                continue;
            }else {
                String[] split = line.split(" ");
                String[] arr = new String[2];
                for (int i = 1,index = 0; i < split.length; i++) {
                    if (StringUtils.isNotBlank(split[i])){
                        arr[index++] = split[i];
                    }
                }
                String apply_no = arr[0];
                PersonApplyInfo applyInfo = new PersonApplyInfo();
                applyInfo.setId(UUID.fastUUID().toString());
                applyInfo.setName(arr[1]);
                applyInfo.setApplyNo(apply_no);
                applyInfo.setApplyPrefix(apply_no.substring(0, 4));
                applyInfo.setApplyInfix(apply_no.substring(4, 8));
                applyInfo.setApplyTail(apply_no.substring(8));
                applyInfo.setIssue(issueNumber);
                list.add(applyInfo);
            }
        }
        return list;
    }

    public static List<CompanyApplyInfo> getCompanyApplyInfo(String text, String issueNumber) throws IOException {
        String lines[] = text.split("\\r?\\n");
        List<CompanyApplyInfo> list = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();
            if (line.contains("：")){
                continue;
            } else if (line.startsWith("-")){
                continue;
            } else if (!line.contains(" ")){
                continue;
            } else if (line.startsWith("序号")){
                continue;
            }else {
                String[] split = line.split("      ");
                String[] arr = new String[2];
                for (int i = 1,index = 0; i < split.length; i++) {
                    if (StringUtils.isNotBlank(split[i])){
                        arr[index++] = split[i];
                    }
                }
                String apply_no = arr[0];
                CompanyApplyInfo applyInfo = new CompanyApplyInfo();
                applyInfo.setId(UUID.fastUUID().toString());
                applyInfo.setName(arr[1]);
                applyInfo.setApplyNo(apply_no);
                applyInfo.setApplyPrefix(apply_no.substring(0, 4));
                applyInfo.setApplyInfix(apply_no.substring(4, 8));
                applyInfo.setApplyTail(apply_no.substring(8));
                applyInfo.setIssue(issueNumber);
                list.add(applyInfo);
            }
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("file/1595836560423.pdf");
        String text = readPdfText(file);
        PersonRecord personRecord = getPersonRecord(text);
        System.out.println(personRecord.toString());
        List<PersonApplyInfo> list = getPersonApplyInfo(text, personRecord.getIssueNum());
        for (PersonApplyInfo personApplyInfo : list) {
            System.out.println(personApplyInfo.toString());
        }
    }
}
