package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("notice_info")
public class NoticeInfo {

    @Id
    private String id;
    private String name;
    private String date;
    private String href;
    private String type;
    private boolean used;
}
