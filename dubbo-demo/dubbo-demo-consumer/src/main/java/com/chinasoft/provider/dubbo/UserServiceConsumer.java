package com.chinasoft.provider.dubbo;

import com.chinasoft.inter.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;

public class UserServiceConsumer {


    @DubboReference
    private UserService userService;

    public void getUser() {
        String user = userService.getUser();
        System.out.println(user);
    }
}
