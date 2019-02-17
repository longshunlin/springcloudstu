package com.cloud.test2.client.impl;

import com.cloud.test2.client.TestFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/26
 */
@Component
public class TestFeignImpl implements TestFeignClient {
    @Override
    public String getFeignTest() {
        return "error";
    }
}
