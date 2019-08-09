package com.crow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName:TestController
 * @Author: wuy2
 * @Description: DONE
 * @Date: Created in 19:40 2019/8/8
 * @Version: V1.0
 */
@Controller
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "wsnewsapp is running!";
    }
}
