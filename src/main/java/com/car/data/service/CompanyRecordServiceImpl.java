package com.car.data.service;

import com.alibaba.fastjson.JSONObject;
import com.car.data.dao.CompanyRecordDao;
import com.car.data.entity.CompanyRecord;
import com.car.data.entity.PersonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyRecordServiceImpl implements CompanyRecordService{
    @Autowired
    CompanyRecordDao companyRecordDao;
    @Override
    public List<CompanyRecord> findNormNum() {
        return companyRecordDao.findNormNum();
    }

    @Override
    public List<CompanyRecord> findApplyNum() {
        return companyRecordDao.findApplyNum();
    }

    @Override
    public List<JSONObject> findInitNum() {
        List<CompanyRecord> companyRecords = companyRecordDao.finadAll();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (CompanyRecord companyRecord : companyRecords) {
            String initialValue = companyRecord.getInitialValue();
            char[] chars = initialValue.toCharArray();
            for (char aChar : chars) {
                int i = Integer.parseInt(String.valueOf(aChar));
                map.merge(i, 1, Integer::sum);
            }
        }
        List<JSONObject> result = new ArrayList<>(map.size());
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number",entry.getKey());
            jsonObject.put("count",entry.getValue());
            result.add(jsonObject);
        }
        return result;
    }
}
