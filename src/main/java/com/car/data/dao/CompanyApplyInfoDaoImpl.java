package com.car.data.dao;

import com.car.data.entity.CompanyApplyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyApplyInfoDaoImpl implements CompanyApplyInfoDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void insertBatch(List<CompanyApplyInfo> companyApplyInfos) {
        if (companyApplyInfos == null || companyApplyInfos.size() == 0)
            return;
        mongoTemplate.insertAll(companyApplyInfos);
    }
}
