package com.car.data.schedules;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.car.data.dao.VisitRecordDao;
import com.car.data.entity.VisitRecord;
import com.car.data.interceptor.VisitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class VisitScheduler {
    @Autowired
    VisitInterceptor visitInterceptor;
    @Autowired
    VisitRecordDao visitRecordDao;

    @Scheduled(cron = "50 59 23 * * ?")
    public void texecute(){
        VisitRecord visitRecord = new VisitRecord();
        visitRecord.setId(UUID.fastUUID().toString());
        visitRecord.setCounts(String.valueOf(visitInterceptor.refresh()));
        visitRecord.setDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        log.info("当日网站访问记录：" + JSONObject.toJSONString(visitRecord));
        visitRecordDao.save(visitRecord);
    }
}
