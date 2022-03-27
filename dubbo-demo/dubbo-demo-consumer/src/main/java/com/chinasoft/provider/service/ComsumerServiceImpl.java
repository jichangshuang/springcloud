package com.chinasoft.provider.service;

import com.chinasoft.inter.service.ConsumerService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ComsumerServiceImpl implements ConsumerService {
    @Override
    public String getConsumer() {
        String str = "我是消费者方法getConsumer()------";
        return str;
    }
}
