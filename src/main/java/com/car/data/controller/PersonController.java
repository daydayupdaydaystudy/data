package com.car.data.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.data.entity.PersonApplyInfo;
import com.car.data.entity.PersonBiddingResult;
import com.car.data.entity.PersonRecord;
import com.car.data.service.PersonApplyInfoService;
import com.car.data.service.PersonBiddingResultService;
import com.car.data.service.PersonRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonBiddingResultService personBiddingResultService;
    @Autowired
    PersonRecordService personRecordService;
    @Autowired
    PersonApplyInfoService personApplyInfoService;

    @ResponseBody
    @PostMapping("/biddingData")
    public Object getPersonBiddingInfo(){
        List<PersonBiddingResult> list = personBiddingResultService.findList();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/biddingDeployNum")
    public Object getPersonBiddingDeployNum(){
        List<PersonBiddingResult> list = personBiddingResultService.findDeployNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/biddingActualNum")
    public Object getPersonBiddingActualNum(){
        List<PersonBiddingResult> list = personBiddingResultService.findActualNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/biddingOveragePrice")
    public Object getPersonBiddingOveragePrice(){
        List<PersonBiddingResult> list = personBiddingResultService.findPrice();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryNormNum")
    public Object getPersonLotteryNormNum(){
        List<PersonRecord> list = personRecordService.findNormNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryApplyNum")
    public Object getPersonLotteryApplyNum(){
        List<PersonRecord> list = personRecordService.findApplyNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryNameTop")
    public Object getPersonLotteryNameTop(){
        List<JSONObject> resultNameTop = personApplyInfoService.findNameTopm();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(resultNameTop));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryInitNum")
    public Object getPersonLotteryInitNum(){
        List<JSONObject> resultInitNum = personRecordService.findInitNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(resultInitNum));
        return objects;
    }

}
