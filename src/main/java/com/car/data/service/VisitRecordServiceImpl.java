package com.car.data.service;

import com.car.data.dao.VisitRecordDao;
import com.car.data.entity.VisitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitRecordServiceImpl implements VisitRecordService {
    @Autowired
    VisitRecordDao visitRecordDao;
    @Override
    public List<VisitRecord> findAll() {
        return visitRecordDao.findAll();
    }
}
