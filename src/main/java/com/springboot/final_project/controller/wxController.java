package com.springboot.final_project.controller;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.springboot.final_project.service.wxService;
import com.springboot.final_project.service.waterService;

import java.io.IOException;

@Controller
public class wxController {

    @Autowired
    wxService wxService;

    @Autowired
    waterService waterService;

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/wx/openid")
    @ResponseBody
    public String getOpenid(String code){
        return wxService.getOpenid(code);
    }

    @RequestMapping("/wx/qrcode")
    @ResponseBody
    public String getQRcode(String openid) throws WriterException, IOException {
        return wxService.getQRcode(openid);
    }

    @RequestMapping("/wx/info")
    @ResponseBody
    public String getInfo(String openid) {
        return wxService.getInfo(openid);
    }

    @CrossOrigin
    @RequestMapping("/waterCheck")
    @ResponseBody
    public String waterCheck(String ticket, String randstr) {
        return waterService.waterCheck(ticket,randstr);
    }
}
