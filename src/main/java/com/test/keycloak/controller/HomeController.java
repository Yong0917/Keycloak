package com.test.keycloak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController {


    @GetMapping("/")
    public String Home(){
        return "main";
    }

    @GetMapping("/main")
    public String Main(){
        return "main";
    }


    @GetMapping("/user_manage")
    public String userManage(){
        return "user_manage";
    }

    /*@GetMapping("/system_manage")
    public String systemManage(){
        return "system_manage";
    }*/

    @GetMapping("/my_info")
    public String myInfo(){
        return "my_info";
    }

    @GetMapping("/pw_manage")
    public String pwManage(){
        return "pw_manage";
    }

    @GetMapping("/err/denied-page")
    public String errPage(){
        return "denied-page";
    }

}
