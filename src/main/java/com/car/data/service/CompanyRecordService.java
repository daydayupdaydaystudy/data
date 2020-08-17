package com.car.data.service;

import com.alibaba.fastjson.JSONObject;
import com.car.data.entity.CompanyRecord;

import java.util.List;

public interface CompanyRecordService {
    List<CompanyRecord> findNormNum();

    List<CompanyRecord> findApplyNum();

    List<JSONObject> findInitNum();
}
