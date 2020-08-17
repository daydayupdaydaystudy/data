package com.car.data.dao;

import com.car.data.entity.CompanyBiddingResult;

import java.util.List;

public interface CompanyBiddingResultDao {
    List<CompanyBiddingResult> findList();

    List<CompanyBiddingResult> findDeployNum();

    List<CompanyBiddingResult> findActualNum();

    List<CompanyBiddingResult> findPrice();
}
