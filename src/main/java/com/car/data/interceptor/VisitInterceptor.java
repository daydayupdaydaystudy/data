package com.car.data.interceptor;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
public class VisitInterceptor implements WebRequestInterceptor {
    private Integer counts = 0;
    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        Date date = new Date();
        ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
        HttpServletRequest request = servletWebRequest.getRequest();
        JSONObject json = new JSONObject();
        json.put("time",DateUtil.format(date,"HH:mm:ss"));
        json.put("date",DateUtil.format(date,"yyyy-MM-dd"));
        json.put("url",request.getRequestURL());
        json.put("uri",request.getRequestURI());
        json.put("ip",request.getRemoteHost());
        log.info("【VisitInterceptor记录请求：】" + json.toJSONString());
        counts++;
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
//        System.out.println("postHandle...");
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
//        System.out.println("afterCompletion...");
    }

    public Integer refresh(){
        Integer temp = counts;
        counts = 0;
        return temp;
    }
}
