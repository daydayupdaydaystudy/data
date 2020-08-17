package com.car.data.dao;

import com.car.data.entity.NoticeInfo;

import java.util.List;

public interface NoticeInfoDao {
    void insertBatch(List<NoticeInfo> noticeInfos);

    NoticeInfo findFirst();

    void used(String id);
}
