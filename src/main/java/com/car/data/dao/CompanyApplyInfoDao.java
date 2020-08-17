package com.car.data.dao;

import com.car.data.entity.CompanyApplyInfo;

import java.util.List;

public interface CompanyApplyInfoDao {
    void insertBatch(List<CompanyApplyInfo> companyApplyInfos);
}
