package com.car.data.dao;

import com.car.data.entity.CompanyRecord;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRecordDaoImpl implements CompanyRecordDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(CompanyRecord companyRecord) {
        mongoTemplate.save(companyRecord);
    }

    @Override
    public List<CompanyRecord> findNormNum() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum", 1);
        fieldDoc.put("normNum", 1);
        BasicQuery query = new BasicQuery(queryDoc, fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC, "issueNum"));
        return mongoTemplate.find(query, CompanyRecord.class);
    }

    @Override
    public List<CompanyRecord> findApplyNum() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum",1);
        fieldDoc.put("applyNum",1);
        fieldDoc.put("lotRate",1);
        BasicQuery query = new BasicQuery(queryDoc,fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC,"issueNum"));
        return mongoTemplate.find(query, CompanyRecord.class);
    }

    @Override
    public List<CompanyRecord> finadAll() {
        return mongoTemplate.findAll(CompanyRecord.class);
    }
}
