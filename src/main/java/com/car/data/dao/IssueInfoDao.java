package com.car.data.dao;

import com.car.data.entity.IssueInfo;

import java.util.List;

public interface IssueInfoDao {

    void save(IssueInfo issueInfo);

    IssueInfo getByIssueNum(String issueNum);

    List<IssueInfo> findUseIssue();

    void update(IssueInfo issueInfo);

    void used(String id);
}
