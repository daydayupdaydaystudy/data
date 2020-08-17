package com.car.data.schedules;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.car.data.dao.IssueInfoDao;
import com.car.data.entity.IssueInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class IssueNumScheduler {

    @Autowired
    IssueInfoDao issueInfoDao;

//    @Scheduled(cron = "0 53 1 1 * ?")
    public void schedule(){
        String issueNum = DateUtil.format(new Date(), "yyyyMM");
        System.out.println(issueNum);
        IssueInfo issueInfo = issueInfoDao.getByIssueNum(issueNum);
        if (issueInfo == null) {
            issueInfo = new IssueInfo();
            issueInfo.setId(UUID.fastUUID().toString());
            issueInfo.setIssueNum(issueNum);
            issueInfo.setUsed(false);
            issueInfoDao.save(issueInfo);
            log.info("【定时任务】添加新的期号："  + issueInfo.toString());
        }
    }
}
