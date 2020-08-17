package com.car.data.dao;

import cn.hutool.core.date.DateUtil;
import com.car.data.entity.VisitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class VisitRecordDaoImpl implements VisitRecordDao {
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public void save(VisitRecord visitRecord) {
        mongoTemplate.save(visitRecord);
    }

    @Override
    public VisitRecord findToday() {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd");
        Query query = Query.query(Criteria.where("date").is(date));
        return mongoTemplate.findAndRemove(query,VisitRecord.class);
    }

    @Override
    public List<VisitRecord> findAll() {
        Query query = Query.query(Criteria.where("_id").ne(""));
        query.with(Sort.by(Sort.Direction.ASC,"date"));
        return mongoTemplate.find(query,VisitRecord.class);
    }
}