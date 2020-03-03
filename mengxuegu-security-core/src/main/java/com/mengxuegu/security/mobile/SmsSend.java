package com.mengxuegu.security.mobile;

/**
 * @auther xukehan
 */
public interface SmsSend {

    /**
     * 发送短信
     * @param mobile 手机号
     * @param contet 发送的内容
     * @return true: 发送成功 false: 发送失败
     */
    boolean sendSms(String mobile,String contet);
}
