package com.car.data.dao;

import com.alibaba.fastjson.JSONObject;
import com.car.data.entity.IssueInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class IssueInfoDaoImpl implements IssueInfoDao {
    private static final String COOLECTION_NAME = "isuue_info";

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(IssueInfo issueInfo) {
        mongoTemplate.save(issueInfo,COOLECTION_NAME);
    }

    @Override
    public IssueInfo getByIssueNum(String issueNum) {
        Query query = Query.query(Criteria.where("issueNum").is(issueNum));
        return mongoTemplate.findOne(query,IssueInfo.class);
    }

    @Override
    public List<IssueInfo> findUseIssue() {
        Query query = Query.query(Criteria.where("used").is(false));
        query.with(Sort.by(Sort.Direction.ASC,"issueNum"));
        return mongoTemplate.find(query,IssueInfo.class);
    }

    @Override
    public void update(IssueInfo issueInfo) {
        Query query = new Query(Criteria.where("_id").is(issueInfo.getId()));
        Update update = new Update();
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(issueInfo));
        json.remove("id");
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            update.set(entry.getKey(), entry.getValue());
        }
        mongoTemplate.updateFirst(query,update,COOLECTION_NAME);
    }

    @Override
    public void used(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("used",true);
        mongoTemplate.updateFirst(query,update,COOLECTION_NAME);
    }
}
