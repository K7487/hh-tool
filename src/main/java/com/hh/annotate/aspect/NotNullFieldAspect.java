//package com.hh.annotate.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
///**
// * @author huanghan
// */
//@Aspect
//@Component
//public class NotNullFieldAspect {
//    @Before("@annotation(com.hh.annotate.NotNullField)")
//    public void beforeNotNullField(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        for (Object arg : args) {
//            if (arg == null) {
//                throw new IllegalArgumentException("参数不能为空");
//            }
//        }
//    }
//}