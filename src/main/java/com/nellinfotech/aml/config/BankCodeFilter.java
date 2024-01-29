//package com.nellinfotech.aml.config;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//@Component
//public class BankCodeFilter implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String bankCode = request.getHeader("bankCode");
//        if (bankCode == null || bankCode.isEmpty()) {
//        	String errorMsg="Missing bankCode header value";
//            response.sendError(HttpStatus.BAD_REQUEST.value(),errorMsg);
//            return false;
//        }
//        return true;
//    }
//
//    // Implement the other methods of HandlerInterceptor if needed
//
//}
