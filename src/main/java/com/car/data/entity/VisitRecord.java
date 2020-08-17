package com.car.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("visit_record")
public class VisitRecord {

    @Id
    private String id;

    private String date;
    private String counts;

}
