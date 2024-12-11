package com.practice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
public class AspectAdditional {
    @Around(value = "com.practice.aop.CommonPointcuts.isFindByIdMethod() && args(id) && target(target)", argNames = "joinPoint,id,target")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Integer id, Object target) throws Throwable {
        log.info("AROUND Invoked findById method in class {} with userId: {}", target, id);
        try {
            Object result = joinPoint.proceed();
            log.info("AROUND Invoked findById method in class {} and returning: {}", target, result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND Invoked findById method in class {} and throwing: {}", target, ex.getClass());
            throw ex;
        } finally {
            log.info("AROUND Invoked findById method in class {} and finally", target);
        }
    }
}
