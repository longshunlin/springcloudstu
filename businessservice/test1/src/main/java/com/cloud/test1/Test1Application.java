package com.cloud.test1;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Description
 * @Author longshunlin
 * @Date 2019/1/25
 */
@EnableCircuitBreaker
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class Test1Application {

    private static final Logger LOG = LoggerFactory.getLogger(Test1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
        LOG.info("====================Info Service started!====================");
    }
}
