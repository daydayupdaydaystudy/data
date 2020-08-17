package com.car.data.dao;

import com.car.data.entity.CompanyRecord;

import java.util.List;

public interface CompanyRecordDao {
    void save(CompanyRecord companyRecord);

    List<CompanyRecord> findNormNum();

    List<CompanyRecord> findApplyNum();

    List<CompanyRecord> finadAll();
}
