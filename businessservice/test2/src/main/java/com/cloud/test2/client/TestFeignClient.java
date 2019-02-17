package com.cloud.test2.client;

import com.cloud.test2.client.impl.TestFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/26
 */
@FeignClient(value = "test1", fallback = TestFeignImpl.class)
public interface TestFeignClient {

    @GetMapping("/test1/feignTest")
    String getFeignTest();
}
