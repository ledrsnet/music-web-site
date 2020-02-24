package com.maple.music.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request,String inputCode){
        //获取服务器生成的验证吗
        String verifyCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //判断输出
        if(inputCode==null||"".equals(inputCode)||"".equals(verifyCode)||verifyCode==null||!verifyCode.equals(inputCode.toLowerCase())){
            return false;
        }
        return true;
    }
}