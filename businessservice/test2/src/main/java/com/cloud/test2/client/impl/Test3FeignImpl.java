package com.cloud.test2.client.impl;

import com.cloud.test2.client.Test3FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/28
 */
@Component
public class Test3FeignImpl implements Test3FeignClient {
    @Override
    public String getTest3FeignTest() {
        return "req test3 fail";
    }
}
