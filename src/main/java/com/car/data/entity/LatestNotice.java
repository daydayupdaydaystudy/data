package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("latest_notice")
public class LatestNotice {

    @Id
    private String id;
    private String name;
}
