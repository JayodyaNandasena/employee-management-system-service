package com.dinethbakers.hrm.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {

    @Pointcut("@annotation(com.dinethbakers.hrm.aop.annotations.RequireRole)")
    public void requireRole() {
    }
}
