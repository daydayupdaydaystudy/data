package com.car.data.service;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.car.data.dao.PersonApplyInfoDao;
import com.car.data.entity.PersonApplyInfo;
import com.car.data.entity.PersonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonApplyInfoServiceImpl implements PersonApplyInfoService {
    @Autowired
    PersonApplyInfoDao personApplyInfoDao;

    @Override
    public List<JSONObject> findNameTopm() {
        List<PersonApplyInfo> all = personApplyInfoDao.findAll();
        HashMap<String, Integer> map = new HashMap<>();
        for (PersonApplyInfo personApplyInfo : all) {
            if (map.get(personApplyInfo.getName()) == null) {
                map.put(personApplyInfo.getName(),1);
            } else {
                map.replace(personApplyInfo.getName(), map.get(personApplyInfo.getName()) + 1);
            }
        }
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<JSONObject> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            JSONObject jsonObject = new JSONObject();
            Map.Entry<String, Integer> entry = list.get(i);
            jsonObject.put("name",entry.getKey());
            jsonObject.put("points",entry.getValue());
            result.add(jsonObject);
        }
        return result;
    }
}
