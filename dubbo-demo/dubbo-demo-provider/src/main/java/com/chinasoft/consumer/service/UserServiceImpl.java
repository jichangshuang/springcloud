package com.chinasoft.consumer.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chinasoft.inter.service.ConsumerService;
import com.chinasoft.inter.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;


@DubboService
public class UserServiceImpl implements UserService {

    @DubboReference
    private ConsumerService consumerService;
    @Override
    public String getUser() {
        String str="";
        Entry entry= null;
        try {
            SphU.entry("");
            str =  "我是dubbo-demo-provider====";
        } catch (BlockException e) {
            e.printStackTrace();
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
//        String consumer = consumerService.getConsumer();



        return str;
    }


    /**
     * 参数要一样，返回值也要一样
     * @return
     */
    @SentinelResource(blockHandler = "testHelloSentinelV2BlockMethod")
    public String testSentinelV2(String a,String b) {
        return "testSentinelV2";
    }

    public String testHelloSentinelV2BlockMethod(String a,String b,BlockException e) {
        System.out.println("方法被流控了");
        return "方法被流控了";

    }
}
