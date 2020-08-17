package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("company_apply_info")
public class CompanyApplyInfo {
    @Id
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 申请编码
     */
    private String applyNo;
    /**
     * 申请前缀
     */
    private String applyPrefix;
    /**
     * 申请中缀
     */
    private String applyInfix;
    /**
     * 申请后缀
     */
    private String applyTail;
    /**
     * 期号
     */
    private String issue;
}
