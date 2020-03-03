package com.mengxuegu.security.controller;

import com.megxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther xukehan
 */
@Controller
public class MoblieLoginController {

    private static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    @Autowired
    private SmsSend smsSend;

    /**
     * 手机验证码
     * @return
     */
    @RequestMapping("/mobile/page")
    public String toMobilePage(){
        return "login-mobile";
    }

    /**
     * 发送手机验证码
     * @return
     */
    @ResponseBody
    @RequestMapping("/code/mobile")
    public MengxueguResult smsCode(HttpServletRequest request){
        //1.生成一个手机验证码
        String code = RandomStringUtils.randomNumeric(4);
        //2.将手机验证码保存到session
        request.getSession().setAttribute(SESSION_KEY,code);
        //3.发送验证码到用户手机上
        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile,code);
        return MengxueguResult.ok();
    }

}
