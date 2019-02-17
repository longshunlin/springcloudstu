package com.cloud.test2.client;

import com.cloud.test2.client.impl.Test3FeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/26
 */
@FeignClient(value = "test3", fallback = Test3FeignImpl.class)
public interface Test3FeignClient {

    @GetMapping("/test3/feignTest")
    String getTest3FeignTest();
}
