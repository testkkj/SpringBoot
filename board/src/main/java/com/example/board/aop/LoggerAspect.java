package com.example.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggerAspect
 */
@Component
@Aspect
public class LoggerAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.example..board.board..controller.*Controller.*(..)) or execution(* com.example..board.board..service.*Impl.*(..)) or execution(* com.example..board.board..dao.*Mapper.*(..))")
    public Object logPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = "";
        String name = proceedingJoinPoint.getSignature().getDeclaringTypeName();

        if (name.indexOf("Controller") > -1) {
            type = "Controller \t: ";
        } else if (name.indexOf("Service") > -1) {
            type = "ServiceImpl \t: ";
        } else if (name.indexOf("Mapper") > -1) {
            type = "Mapper \t: ";
        }

        log.debug(type + name + "." + proceedingJoinPoint.getSignature().getName() + "()");
        return proceedingJoinPoint.proceed();
    }
}