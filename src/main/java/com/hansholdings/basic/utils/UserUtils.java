package com.hansholdings.basic.utils;

import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserUtils {

    public static String getCurrentUserName() {
        return getCurrentUser().getUsername();
    }

    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (User) session.getAttribute(Constants.USER_SESSION_ID);
    }

}
