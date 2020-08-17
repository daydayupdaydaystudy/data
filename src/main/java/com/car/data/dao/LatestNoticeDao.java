package com.car.data.dao;

import com.car.data.entity.LatestNotice;

public interface LatestNoticeDao {
    LatestNotice getLastNoticeInstance();

    void updateInstance(LatestNotice latestNotice);
}
