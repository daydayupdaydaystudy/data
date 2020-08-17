package com.car.data.dao;

import com.car.data.entity.CompanyBiddingResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyBiddingResultDaoImpl implements CompanyBiddingResultDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<CompanyBiddingResult> findList() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("averagePrice", 1);
        fieldDoc.put("issueNum", 1);
        fieldDoc.put("actualNum", 1);
        fieldDoc.put("deployNum", 1);
        BasicQuery query = new BasicQuery(queryDoc, fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC, "issueNum"));
        return mongoTemplate.find(query, CompanyBiddingResult.class);
    }

    @Override
    public List<CompanyBiddingResult> findDeployNum() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum", 1);
        fieldDoc.put("deployNum", 1);
        BasicQuery query = new BasicQuery(queryDoc, fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC, "issueNum"));
        return mongoTemplate.find(query, CompanyBiddingResult.class);
    }

    @Override
    public List<CompanyBiddingResult> findActualNum() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum", 1);
        fieldDoc.put("actualNum", 1);
        BasicQuery query = new BasicQuery(queryDoc, fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC, "issueNum"));
        return mongoTemplate.find(query, CompanyBiddingResult.class);
    }

    @Override
    public List<CompanyBiddingResult> findPrice() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum", 1);
        fieldDoc.put("secondAveragePrice", 1);
        fieldDoc.put("minPrice", 1);
        fieldDoc.put("averagePrice", 1);
        BasicQuery query = new BasicQuery(queryDoc, fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC, "issueNum"));
        return mongoTemplate.find(query, CompanyBiddingResult.class);
    }
}
