package com.car.data.service;

import com.car.data.entity.PersonBiddingResult;

import java.util.List;

public interface PersonBiddingResultService {
    List<PersonBiddingResult> findList();

    List<PersonBiddingResult> findDeployNum();

    List<PersonBiddingResult> findActualNum();

    List<PersonBiddingResult> findPrice();
}
