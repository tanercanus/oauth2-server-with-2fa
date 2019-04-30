package com.rr.security.authserver.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyCustomInterceptor implements HandlerInterceptor {

    //unimplemented methods comes here. Define the following method so that it
    //will handle the request before it is passed to the controller.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //your custom logic here.
        return true;
    }
}