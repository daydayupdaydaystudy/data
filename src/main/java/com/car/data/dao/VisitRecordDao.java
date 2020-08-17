package com.car.data.dao;

import com.car.data.entity.VisitRecord;

import java.util.List;

public interface VisitRecordDao {
    void save(VisitRecord visitRecord);

    VisitRecord findToday();

    List<VisitRecord> findAll();
}
