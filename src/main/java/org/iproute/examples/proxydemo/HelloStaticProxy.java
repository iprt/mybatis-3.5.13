package org.iproute.examples.proxydemo;

import lombok.AllArgsConstructor;

/**
 * HelloStaticProxy
 *
 * @author zhuzhenjie
 * @since 3/21/2023
 */
@AllArgsConstructor
public class HelloStaticProxy implements Hello, BeforeAfter {
    private final Hello hello;

    @Override
    public String sayHello(String name) {
        this.before();
        String result = this.hello.sayHello(name);
        this.after();
        return result;
    }
}
