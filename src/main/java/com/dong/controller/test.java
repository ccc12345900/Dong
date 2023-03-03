package com.dong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 橙宝cc
 * @date 2023/3/2 - 23:00
 */
@Controller
public class test {

    @RequestMapping("/")
    public String test()
    {
        return "login";
    }

}
