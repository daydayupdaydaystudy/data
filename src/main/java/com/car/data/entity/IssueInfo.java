package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("isuue_info")
public class IssueInfo {
    @Id
    private String id;
    private String issueNum;
    private boolean used;

    @Override
    public String toString() {
        return "IssueInfo{" +
                "id='" + id + '\'' +
                ", issueNum='" + issueNum + '\'' +
                ", used=" + used +
                '}';
    }
}
