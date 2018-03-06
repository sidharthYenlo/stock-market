package com.sidharth.demo.springcloud.controller;

import com.sidharth.demo.springcloud.core.Dto.PriceDTO;
import com.sidharth.demo.springcloud.core.Dto.StockDTO;
import com.sidharth.demo.springcloud.core.Dto.StockPriceWrapperDTO;
import com.sidharth.demo.springcloud.core.Dto.UserDTO;
import com.sidharth.demo.springcloud.core.ServiceImpl.UserServiceImpl;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
