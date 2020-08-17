package com.car.data.dao;

import com.car.data.entity.PersonRecord;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRecordDaoImpl implements PersonRecordDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(PersonRecord personRecord) {
        mongoTemplate.save(personRecord);
    }

    @Override
    public List<PersonRecord> findNormNum() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum",1);
        fieldDoc.put("normNum",1);
        BasicQuery query = new BasicQuery(queryDoc,fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC,"issueNum"));
        return mongoTemplate.find(query, PersonRecord.class);
    }

    @Override
    public List<PersonRecord> findApplyNum() {
        Document queryDoc = new Document();
        Document fieldDoc = new Document();
        fieldDoc.put("issueNum",1);
        fieldDoc.put("applyNum",1);
        fieldDoc.put("lotRate",1);
        BasicQuery query = new BasicQuery(queryDoc,fieldDoc);
        query.with(Sort.by(Sort.Direction.ASC,"issueNum"));
        return mongoTemplate.find(query, PersonRecord.class);
    }

    @Override
    public List<PersonRecord> finadAll() {
        return mongoTemplate.findAll(PersonRecord.class);
    }
}
