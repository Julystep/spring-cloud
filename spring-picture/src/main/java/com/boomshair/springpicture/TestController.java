package com.boomshair.springpicture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liml28084
 * @Date: 2021/9/14
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

}
