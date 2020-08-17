package com.car.data.service;

import com.car.data.dao.CompanyBiddingResultDao;
import com.car.data.entity.CompanyBiddingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyBiddingResultServiceImpl implements CompanyBiddingResultService {
    @Autowired
    CompanyBiddingResultDao companyBiddingResultDao;
    @Override
    public List<CompanyBiddingResult> findList() {
        return companyBiddingResultDao.findList();
    }

    @Override
    public List<CompanyBiddingResult> findDeployNum() {
        return companyBiddingResultDao.findDeployNum();
    }

    @Override
    public List<CompanyBiddingResult> findActualNum() {
        return companyBiddingResultDao.findActualNum();
    }

    @Override
    public List<CompanyBiddingResult> findPrice() {
        return companyBiddingResultDao.findPrice();
    }
}
