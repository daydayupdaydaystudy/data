package com.car.data.dao;

import com.car.data.entity.PersonBiddingResult;

import java.util.List;

public interface PersonBiddingResultDao {
    List<PersonBiddingResult> findList();

    List<PersonBiddingResult> findDeployNum();

    List<PersonBiddingResult> findActualNum();

    List<PersonBiddingResult> findPrice();
}
