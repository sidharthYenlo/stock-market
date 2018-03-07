package com.sidharth.demo.springcloud.controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author sidharthdash ON 3/5/18
 */

@RestController
public class UserController {

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }
}
