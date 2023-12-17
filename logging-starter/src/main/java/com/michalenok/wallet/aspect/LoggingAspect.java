package com.michalenok.wallet.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Log4j2
@Aspect
public class LoggingAspect {  @Pointcut("@annotation(com.michalenok.wallet.aspect.Logged)")
private void annotationPointcut() {
}
    @Before("com.michalenok.wallet.aspect.LoggingAspect.annotationPointcut()")
    public void logMethodBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        log.info("Received call :: {} - request params [{}, {}]", methodName, signature.getParameterNames(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "com.michalenok.wallet.aspect.LoggingAspect.annotationPointcut()", returning = "response")
    public void logMethodAfter(JoinPoint joinPoint, Object response) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();

        log.info("Сallback :: {}, response params  = {}",
                methodName, response);
    }
}