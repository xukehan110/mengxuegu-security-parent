package com.mengxuegu.security.mobile.Impl;

import com.mengxuegu.security.mobile.SmsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther xukehan
 */

//@Component
public class SmsCodeSender implements SmsSend {

    Logger logger  = LoggerFactory.getLogger(getClass());
    /**
     * @param mobile 手机号
     * @param contet 发送的内容: 内容（短信验证码）
     * @return
     */
    @Override
    public boolean sendSms(String mobile, String contet) {
        String sendContent = String.format("梦学谷，验证码%s，请勿泄漏", contet);
        //调用第三发发送功能的sdk
        logger.info("向"+mobile+"手机发送验证码,"+sendContent);
        return true;
    }
}
