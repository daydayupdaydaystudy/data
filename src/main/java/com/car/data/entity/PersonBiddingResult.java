package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("person_bidding_result")
public class PersonBiddingResult {

    @Id
    private String id;
    /**
     * 竞价期数
     */
    private String issueNum;
    /**
     * 投放
     * 指标数
     */
    private String deployNum;
    /**
     * 有效
     * 编码数
     */
    private String actualNum;
    /**
     * 第一次
     * 播报均价
     */
    private String firstAveragePrice;
    /**
     * 第二次
     * 播报均价
     */
    private String secondAveragePrice;
    /**
     * 最低
     * 成交价
     */
    private String minPrice;
    /**
     * 平均
     * 成交价
     */
    private String averagePrice;
    /**
     * 最低成交价
     * 报价编码数
     */
    private String minPriceOfferNum;
    /**
     * 最低成交价
     * 成交编码数
     */
    private String minPriceDealNum;
    /**
     * 成交
     * 编码数
     */
    private String dealNUm;
    /**
     * 实际付款
     * 指标数
     */
    private String payNum;
}
