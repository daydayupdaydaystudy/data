package com.car.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration()
public class CarConfig {

    @Value("${car-config.apply-info-url}")
    public String applyInfoUrl;
    @Value("${car-config.notice-info-url}")
    public String noticeInfoUrl;

    //@Bean  mongodb 3.6版本不支持事务
    MongoTransactionManager transactionManager(MongoDatabaseFactory factory){
        return new MongoTransactionManager(factory);
    }


}
