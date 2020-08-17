package com.car.data.dao;

import cn.hutool.core.lang.UUID;
import com.car.data.entity.LatestNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LatestNoticeDaoImpl implements LatestNoticeDao {
    private static final String COLLECTION_NAME = "latest_notice";
    @Autowired
    MongoTemplate mongoTemplate;

    private LatestNotice latestNotice;

    @Override
    public LatestNotice getLastNoticeInstance() {
        if (this.latestNotice == null) {
            List<LatestNotice> all = mongoTemplate.findAll(LatestNotice.class);
            if (all != null && all.size() > 0) {
                this.latestNotice = all.get(0);
            }
        }
        return this.latestNotice;
    }

    @Override
    public void updateInstance(LatestNotice latestNotice) {
        if (latestNotice.getId() == null) {
            latestNotice.setId(UUID.fastUUID().toString());
        }
        mongoTemplate.save(latestNotice,COLLECTION_NAME);
        this.latestNotice = latestNotice;
    }
}
