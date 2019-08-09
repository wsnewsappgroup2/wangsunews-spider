package com.crow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:TestController
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 19:40 2019/8/8
 * @Version: V1.0
 */
@RestController
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String test() {
        logger.info("Test Controller Log Info");
        return "wsnewsapp is running!";
    }
}
