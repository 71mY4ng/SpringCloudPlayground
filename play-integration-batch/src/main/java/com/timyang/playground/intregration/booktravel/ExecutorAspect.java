package com.timyang.playground.intregration.booktravel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class ExecutorAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorAspect.class);

    private Function<String, Void> beforeExecution;
    private Function<String, Void> afterExecution;

    public void aroundExe(ProceedingJoinPoint pjp, String name) {
        try {
            doBefore(name);
            pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            doFinally(name);
        }
    }

    private void doFinally(String name) {
        LOGGER.info("************* finally after " + name);
        if (afterExecution != null)
            afterExecution.apply(name);
    }

    private void doBefore(String name) {
        LOGGER.info("************* before execution " + name);
        if (beforeExecution != null)
            beforeExecution.apply(name);
    }

    public Function<String, Void> getBeforeExecution() {
        return beforeExecution;
    }

    public void setBeforeExecution(Function<String, Void> beforeExecution) {
        this.beforeExecution = beforeExecution;
    }

    public Function<String, Void> getAfterExecution() {
        return afterExecution;
    }

    public void setAfterExecution(Function<String, Void> afterExecution) {
        this.afterExecution = afterExecution;
    }
}
