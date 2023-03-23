package org.iproute.examples.proxydemo;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * HelloDynamicProxy
 *
 * @author zhuzhenjie
 * @since 3/21/2023
 */
@AllArgsConstructor
public class HelloDynamicProxy implements InvocationHandler, BeforeAfter {
    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.before();
        Object result = method.invoke(target, args);
        this.after();
        return result;
    }
}
