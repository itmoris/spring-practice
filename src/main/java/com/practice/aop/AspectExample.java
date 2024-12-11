package com.practice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class AspectExample {

    /*
     *
     * args - return argument value
     * this - return proxy class
     * target - return target class
     * @args - return annotation on argument
     * @within - return annotation on class
     * @annotation - return annotation on method
     *
     * signature: && args(argName, argName) && target(argName)
     * note: JoinPoint always must be first argument in advice method
     * */
        @Before(value = "com.practice.aop.CommonPointcuts.isFindByIdMethod() && args(id) && target(target)", argNames = "joinPoint,id,target")
        public void addLogging(JoinPoint joinPoint, Integer id, Object target) {
            log.info("Invoked findById method in class {} with userId: {}", target, id);
        }

        @AfterReturning(value = "com.practice.aop.CommonPointcuts.isFindByIdMethod() && target(target)", returning = "result", argNames = "joinPoint,target,result")
        public void addLoggingAfterReturning(JoinPoint joinPoint, Object target, Object result) {
            log.info("Invoked findById method in class {} and returning: {}", target, result);
        }

        @AfterThrowing(value = "com.practice.aop.CommonPointcuts.isFindByIdMethod() && target(target)", throwing = "ex")
        public void addLoggingAfterThrowing(JoinPoint joinPoint, Object target, Throwable ex) {
            log.info("Invoked findById method in class {} and throwing: {}", target, ex.getClass());
        }

        @After(value = "com.practice.aop.CommonPointcuts.isFindByIdMethod() && target(target)")
        public void addLoggingAfterFinally(JoinPoint joinPoint, Object target) {
            log.info("Invoked findById method in class {} and finally", target);
        }
}
