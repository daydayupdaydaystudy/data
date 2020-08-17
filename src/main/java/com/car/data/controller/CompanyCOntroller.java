package com.car.data.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.data.entity.CompanyBiddingResult;
import com.car.data.entity.CompanyRecord;
import com.car.data.service.CompanyApplyInfoService;
import com.car.data.service.CompanyBiddingResultService;
import com.car.data.service.CompanyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("company")
public class CompanyCOntroller {

    @Autowired
    CompanyRecordService companyRecordService;
    @Autowired
    CompanyBiddingResultService companyBiddingResultService;
    @Autowired
    CompanyApplyInfoService companyApplyInfoService;

    @ResponseBody
    @PostMapping("/biddingData")
    public Object getCompanyBiddingInfo(){
        List<CompanyBiddingResult> list = companyBiddingResultService.findList();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/biddingDeployNum")
    public Object getCompanyBiddingDeployNum(){
        List<CompanyBiddingResult> list = companyBiddingResultService.findDeployNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/biddingActualNum")
    public Object getCompanyBiddingActualNum(){
        List<CompanyBiddingResult> list = companyBiddingResultService.findActualNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/biddingOveragePrice")
    public Object getCompanyBiddingOveragePrice(){
        List<CompanyBiddingResult> list = companyBiddingResultService.findPrice();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryNormNum")
    public Object getCompanyLotteryNormNum(){
        List<CompanyRecord> list = companyRecordService.findNormNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryApplyNum")
    public Object getPersonLotteryApplyNum(){
        List<CompanyRecord> list = companyRecordService.findApplyNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(list));
        return objects;
    }

    @ResponseBody
    @PostMapping("/lotteryInitNum")
    public Object getPersonLotteryInitNum(){
        List<JSONObject> resultInitNum = companyRecordService.findInitNum();
        JSONArray objects = JSONArray.parseArray(JSONArray.toJSONString(resultInitNum));
        return objects;
    }
}
