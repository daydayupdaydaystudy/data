package com.car.data.dao;

import com.car.data.entity.NoticeInfo;
import com.car.data.enums.NoticeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeInfoDaoImpl implements NoticeInfoDao {
    private static final String COLLECTION_NAME = "notice_info";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertBatch(List<NoticeInfo> noticeInfos) {
        if (noticeInfos == null || noticeInfos.size() ==0)
            return;
        mongoTemplate.insertAll(noticeInfos);
    }

    @Override
    public NoticeInfo findFirst() {
        Query query = Query.query(Criteria.where("type").is(NoticeEnum.Notice_1.getCode()).and("used").is(false));
        return mongoTemplate.findOne(query,NoticeInfo.class);
    }

    @Override
    public void used(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("used",true);
        mongoTemplate.updateFirst(query,update,COLLECTION_NAME);
    }
}
