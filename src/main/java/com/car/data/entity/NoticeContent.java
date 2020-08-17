package com.car.data.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("notice_content")
public class NoticeContent {

    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 个人指标计划配置数
     */
    private String personPlanNum;
    /**
     * 个人有效申请编码数
     */
    private String personApplyNum;
    /**
     * 个人实际配置指标数
     */
    private String personActualNum;
    /**
     * 单位指标计划配置数
     */
    private String companyPlanNum;
    /**
     * 单位有效申请编码数
     */
    private String companyApplyNum;
    /**
     * 单位实际配置指标数
     */
    private String companyActualNum;
    /**
     * 合计指标计划配置数
     */
    private String sumPlanNum;
    /**
     * 合计有效申请编码数
     */
    private String sumApplyNum;
    /**
     * 合计实际配置指标数
     */
    private String sumActualNum;
    /**
     * 个人指标配置结果文件名
     */
    private String personFileName;
    /**
     * 个人指标配置结果文件地址
     */
    private String personFileUrl;
    /**
     * 个人指标配置结果文件名
     */
    private String companyFileName;
    /**
     * 单位指标配置结果文件地址
     */
    private String companyFileUrl;
    /**
     * 期号
     */
    private String issueNum;

}
