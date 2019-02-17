package com.cloud.test3.controller;

import com.cloud.test3.client.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/28
 */
@RestController
public class HiController {

    @Autowired
    HiService hiService;

    @RequestMapping("/hi")
    public String hi(String name) {
        return hiService.hi(name);
    }
}
