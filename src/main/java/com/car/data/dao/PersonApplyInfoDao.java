package com.car.data.dao;

import com.car.data.entity.PersonApplyInfo;
import com.car.data.entity.PersonRecord;

import java.util.List;

public interface PersonApplyInfoDao {
    void insertBatch(List<PersonApplyInfo> list);

    List<PersonApplyInfo> findAll();
}
