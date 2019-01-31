package com.example.courseanalyzer.controller;
/**
 * @Package: com.example.courseanalyzer.controller
 * @Class: HomeController
 * @Author: Jan
 * @Date: 25.01.2019
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
