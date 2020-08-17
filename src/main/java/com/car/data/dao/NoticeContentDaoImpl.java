package com.car.data.dao;

import com.car.data.entity.NoticeContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeContentDaoImpl implements NoticeContentDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(NoticeContent noticeContent) {
        mongoTemplate.save(noticeContent);
    }
}
