package com.mengxuegu.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CustomLoginController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @RequestMapping("/login/page")
    public String toLogin(){
        return "login";
    }

    /**
     * 获取图形验证码
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.从图片中获取验证码
        String code = defaultKaptcha.createText();
        //2.并放入session中
        request.getSession().setAttribute(SESSION_KEY,code);
        //3.获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        //4.将验证码写出去
        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(image,"jpg",outputStream);
    }

}
