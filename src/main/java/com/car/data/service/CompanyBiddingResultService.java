package com.car.data.service;

import com.car.data.entity.CompanyBiddingResult;

import java.util.List;

public interface CompanyBiddingResultService {
    List<CompanyBiddingResult> findList();

    List<CompanyBiddingResult> findDeployNum();

    List<CompanyBiddingResult> findActualNum();

    List<CompanyBiddingResult> findPrice();
}
