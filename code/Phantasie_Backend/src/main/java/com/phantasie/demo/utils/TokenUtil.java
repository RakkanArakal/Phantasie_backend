package com.phantasie.demo.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenUtil {
    protected static String MD5(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte s[] = md.digest();
            String result = "";

            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result.substring(0,6);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected static String SHA256(String str){
        MessageDigest messageDigest;
        String encodeStr;
        encodeStr = str;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr.substring(5,10);
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String generate() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int ran = 0;
        ran = (int)(Math.random()*100);
        String rannum = String.valueOf((int)(Math.random()*100000));
        String param1 = String.valueOf((int)(Math.random()*233333));
        String param2 = String.valueOf((int)(Math.random()*25565));
        String ret;
//        System.out.println(rannum);
        if(ran %2 == 1){
            ret = (MD5(param1) + SHA256(param2) +MD5(rannum));
        }else {
            ret = (MD5(rannum) + SHA256(param1) + MD5(param2));
        }
        return ret;
    }
}
