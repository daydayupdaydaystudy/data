package com.car.data.service;

import com.alibaba.fastjson.JSONObject;
import com.car.data.dao.PersonRecordDao;
import com.car.data.entity.PersonApplyInfo;
import com.car.data.entity.PersonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonRecordServiceImpl implements PersonRecordService {
    @Autowired
    PersonRecordDao personRecordDao;
    @Override
    public List<PersonRecord> findNormNum() {
        return personRecordDao.findNormNum();
    }

    @Override
    public List<PersonRecord> findApplyNum() {
        return personRecordDao.findApplyNum();
    }

    @Override
    public List<JSONObject> findInitNum() {
        List<PersonRecord> personRecords = personRecordDao.finadAll();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (PersonRecord personRecord : personRecords) {
            String initialValue = personRecord.getInitialValue();
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
