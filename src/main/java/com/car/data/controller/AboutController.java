package com.car.data.controller;

import com.alibaba.fastjson.JSONArray;
import com.car.data.entity.VisitRecord;
import com.car.data.service.VisitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    VisitRecordService visitRecordService;

    @PostMapping("/visits")
    public Object visits(){
        List<VisitRecord> list =  visitRecordService.findAll();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }
}
