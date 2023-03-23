package org.iproute.examples.proxydemo;

/**
 * HelloWorld
 *
 * @author zhuzhenjie
 * @since 3/21/2023
 */
public class HelloWorld implements Hello {
    @Override
    public String sayHello(String name) {
        return "hello world " + name;
    }
}
