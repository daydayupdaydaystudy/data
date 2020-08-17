package com.car.data.service;

import com.alibaba.fastjson.JSONArray;
import com.car.data.dao.PersonBiddingResultDao;
import com.car.data.entity.PersonBiddingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonBiddingResultServiceImpl implements PersonBiddingResultService {
    @Autowired
    PersonBiddingResultDao personBiddingResultDao;

    @Override
    public List<PersonBiddingResult> findList() {
        return personBiddingResultDao.findList();
    }

    @Override
    public List<PersonBiddingResult> findDeployNum() {
        return personBiddingResultDao.findDeployNum();
    }

    @Override
    public List<PersonBiddingResult> findActualNum() {
        return personBiddingResultDao.findActualNum();
    }

    @Override
    public List<PersonBiddingResult> findPrice() {
        return personBiddingResultDao.findPrice();
    }
}
