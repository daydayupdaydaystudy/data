package com.car.data.dao;

import com.car.data.entity.PersonApplyInfo;
import com.car.data.entity.PersonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonApplyInfoDaoImpl implements PersonApplyInfoDao {
    private static final String COOLECTION_NAME = "person_apply_info";
    private static List<PersonApplyInfo> list = null;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void insertBatch(List<PersonApplyInfo> list) {
        if (list == null || list.size() == 0)
            return;
        mongoTemplate.insertAll(list);
        this.list = null;
    }

    @Override
    public List<PersonApplyInfo> findAll() {
        if (list == null) {
            list = mongoTemplate.findAll(PersonApplyInfo.class);
        }
        return list;
    }
}
