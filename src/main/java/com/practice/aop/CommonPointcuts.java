package com.practice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {
    /*
     * @within - check annotation on class
     * */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
     * within - check class type name
     * */
    @Pointcut("within(com.practice.service.*Service)")
    public void isServiceLayer() {
    }

    /*
     *  this - check proxy type
     *  target - check class type
     * */
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer() {
    }

    /*
     * bean - check name of bean
     * */
    @Pointcut("bean(*Repository)")
    public void isControllerLayerBean() {
    }

    /*
     * @annotation - annotation on method
     * */
    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
//    @Pointcut("isControllerLayer() || @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
     * args - check args types
     * * - 1 any args
     * .. - 0+ any args
     * */
    @Pointcut("args(org.springframework.ui.Model,..)")
//    @Pointcut("args(org.springframework.ui.Model,*)")
    public void hasModelParam() {
    }

    /*
     * @args - annotation on param type
     * */
    @Pointcut("@args(jakarta.persistence.Entity,..)")
    public void hasAnnotationOnParam() {
    }

    /*
     * execution - check method execution pattern
     *
     * signature: execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
     * */
    @Pointcut("execution(public * com.practice.service.*Service.findById(..))")
    public void isFindByIdMethod() {
    }
}
