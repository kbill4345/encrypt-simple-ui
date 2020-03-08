package com.yln.controller;

import com.yln.util.ThreeDES;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: taochen
 * @create: 2020-03-07 14:02
 **/
@Controller
public class EncryptController {

    @PostMapping("/api/encrypt")
    @ResponseBody
    public String encrypt(@RequestParam("message") String message){

        return ThreeDES.encryptThreeDESECB(message);
    }

    @PostMapping("/api/decrypt")
    @ResponseBody
    public String decrypt(@RequestParam("message") String message){
        return ThreeDES.decryptThreeDESECB(message);
    }
}
