package com.car.data.service;

import com.alibaba.fastjson.JSONObject;
import com.car.data.entity.PersonRecord;

import java.util.List;

public interface PersonRecordService {
    List<PersonRecord> findNormNum();

    List<PersonRecord> findApplyNum();

    List<JSONObject> findInitNum();
}
