package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("company_record")
public class CompanyRecord {

    @Id
    private String id;
    /**
     * 本期编号
     */
    private String issueNum;
    /**
     * 本期描述
     */
    private String description;
    /**
     * 本期指标配置日期
     */
    private String deployDate;
    /**
     * 实际指标配置时间
     */
    private String deployDateTime;
    /**
     * 有效单位申请编码总数
     */
    private String applyNum;
    /**
     * 配置单位普通指标总数
     */
    private String normNum;
    /**
     * 指标配置初始值
     */
    private String initialValue;
    /**
     * 中签率
     */
    private String lotRate;
}
