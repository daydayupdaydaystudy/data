package com.car.data.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.car.data.dao.VisitRecordDao;
import com.car.data.entity.VisitRecord;
import com.car.data.interceptor.VisitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 监听容器生命周期，在容器关闭时执行监听方法
 */
@Slf4j
@Component
public class VisitRecordListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    VisitRecordDao visitRecordDao;
    @Autowired
    VisitInterceptor visitInterceptor;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("监听到应用程序即将关闭，网站流量监听器开始工作");
        VisitRecord visitRecord = visitRecordDao.findToday();
        if (visitRecord == null){
            visitRecord = new VisitRecord();
            visitRecord.setId(UUID.fastUUID().toString());
            visitRecord.setDate(DateUtil.format(new Date(),"yyyy-MM-dd"));
            visitRecord.setCounts("0");
        }
        Integer counts = Integer.parseInt(visitRecord.getCounts()) + visitInterceptor.refresh();
        visitRecord.setCounts(String.valueOf(counts));
        visitRecordDao.save(visitRecord);
    }
}
