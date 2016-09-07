package com.stefli.db.core;

import java.lang.reflect.Field;
import java.util.Random;

import com.stefli.db.main.Main;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mysql.jdbc.Connection;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAdvice {

    @Around("execution (* com.stefli.db.service.*.*(..))")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        final String methodName = joinPoint.getSignature().getName();
        final DataSourceType[] slaves = new DataSourceType[]{
                DataSourceType.SLAVE1, DataSourceType.SLAVE2};
        final Random random = new Random();
        try {
            if (methodName.startsWith("find")) {
                CustomContextHolder.setDataSourceType(slaves[random.nextInt(2)]);
            } else {
                CustomContextHolder.setDataSourceType(DataSourceType.MASTER);
            }

            DataSourceTransactionManager dtm = (DataSourceTransactionManager)
                    Main.springApplicationContext().getBean("transactionManager");
            Connection conn = (Connection) dtm.getDataSource().getConnection();

            Field field = conn.getClass().getDeclaredField("myURL");
            field.setAccessible(true);
            System.out.println("Method[" + methodName + "] --> " + field.get(conn));
            return joinPoint.proceed();
        } catch (IllegalArgumentException e) {
            throw e;
        } finally {
            CustomContextHolder.clearDataSourceType();
        }
    }

}
