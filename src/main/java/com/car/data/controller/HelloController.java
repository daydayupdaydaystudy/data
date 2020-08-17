package com.car.data.controller;

import com.alibaba.fastjson.JSONArray;
import com.car.data.entity.PersonBiddingResult;
import com.car.data.service.PersonBiddingResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    PersonBiddingResultService personBiddingResultService;

    @GetMapping("/index")
    public String index(){
        return "view/totalnum.html";
    }

    @ResponseBody
    @PostMapping("/hello")
    public String hello(){
        return "hello world";
    }

}
