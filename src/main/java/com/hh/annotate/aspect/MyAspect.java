//package com.hh.annotate.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class MyAspect {
//    @Before("@annotation(com.hh.annotate.MyAnnotation)") // 前置通知，当有@MyAnnotation注解的方法调用之前会执行此通知
//    public void beforeAdvice() {
//        System.out.println("执行前置通知");
//    }
//
//    @AfterReturning("@annotation(com.hh.annotate.MyAnnotation)") // 后置返回通知，当有@MyAnnotation注解的方法正常返回时会执行此通知
//    public void afterReturningAdvice() {
//        System.out.println("执行后置返回通知");
//    }
//
//    @Around("@annotation(com.hh.annotate.MyAnnotation)") // 环绕通知，包含了前置、后置等所有通知
//    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("执行环绕通知 - 开始");
//        try {
//            return joinPoint.proceed(); // 执行原方法
//        } finally {
//            System.out.println("执行环绕通知 - 结束");
//        }
//    }
//}
