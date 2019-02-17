package com.cloud.test2.controller;

import com.cloud.test2.client.Test3FeignClient;
import com.cloud.test2.client.TestFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/25
 */
@RestController
@RequestMapping(value = "/test2")
@Api(description = "测试接口类")
public class TestController {

    @Autowired
    private TestFeignClient testFeignClient;

    @Autowired
    private Test3FeignClient test3FeignClient;

    @Value("${testNum}")
    private String testNum;

    @GetMapping("/getInfo")
    @ApiOperation(value = "测试接口方法")
    public Object getInfo() {
        System.out.println(testNum);
        return "this is get self info";
    }

    @GetMapping("/getFeignTest")
    @ApiOperation(value = "测试接口方法2")
    public Object getFeignTest() {
        return testFeignClient.getFeignTest();
    }

    @GetMapping("/getTest3FeignTest")
    @ApiOperation(value = "测试接口方法4")
    public Object getTest3FeignTest() {
        return test3FeignClient.getTest3FeignTest();
    }

    @GetMapping("/getTest")
    @ApiOperation(value = "测试接口方法3")
    public Object getTest() {
        return testNum;
    }
}
