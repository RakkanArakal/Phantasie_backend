package com.phantasie.demo.utils;

//import com.example.demo.constant.Constant;
import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static boolean checkAuth(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // Session
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);

            String url = request.toString();
            System.out.println(url);

            if(session != null) {
                return true;
            }
        }
        return false;
    }

    public static JSONObject getAuth(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // Session
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);

            if(session != null) {
                JSONObject ret = new JSONObject();
                //TODO:加上会话存在会返回的信息
<<<<<<< HEAD
                ret.put("userId", (Integer)session.getAttribute("user_id"));
=======

                ret.put("userId", (Integer)session.getAttribute("userId"));
<<<<<<< HEAD
>>>>>>> 93f6a3b (9.13)
                ret.put("username", (String)session.getAttribute("name"));
=======
                ret.put("userName", (String)session.getAttribute("name"));

>>>>>>> 5e11ab6 (10:06)
                //ret.put(Constant.USER_TYPE, (Integer)session.getAttribute(Constant.USER_TYPE));
                return ret;
            }
        }
        return null;
    }

    public static void setSession(JSONObject data){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // Session
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();

            for(Object str:data.keySet()){
                String key = (String)str;
                Object val = data.get(key);
                session.setAttribute(key, val);
            }
        }
    }

    public static String setToken(){
        ServletRequestAttributes requestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();
            return session.getId();
        }
        return "";
    }

    public static Boolean removeSession(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // Session
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);

            if(session != null) {
                session.invalidate();
            }
        }
        return true;
    }
}
