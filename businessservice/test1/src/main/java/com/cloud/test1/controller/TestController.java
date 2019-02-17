package com.cloud.test1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/25
 */
@RestController
@RequestMapping(value = "/test1")
@Api(description = "测试接口类")
public class TestController {
    @Value("${testNum}")
    private String testNum;
    @Value("${test-version}")
    private String testVersion;

    @GetMapping("/getInfo")
    @ApiOperation(value = "测试接口方法")
    public Object getInfo() {
        System.out.println(testNum);
        System.out.println(testVersion);
        return "this is get self info";
    }

    @GetMapping("/feignTest")
    public Object feignTest() {
        return new ArrayList<String>();
    }
}
