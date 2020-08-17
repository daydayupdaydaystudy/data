package com.car.data.dao;

import com.car.data.entity.PersonRecord;

import java.util.List;

public interface PersonRecordDao {
    void save(PersonRecord personRecord);

    List<PersonRecord> findNormNum();

    List<PersonRecord> findApplyNum();

    List<PersonRecord> finadAll();
}
