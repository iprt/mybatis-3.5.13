package org.iproute.examples.proxydemo;

/**
 * BeforeAfter
 *
 * @author zhuzhenjie
 * @since 3/21/2023
 */
public interface BeforeAfter {

    default void before() {
        System.out.println("BeforeAfter.before");
    }

    default void after() {
        System.out.println("BeforeAfter.after");
    }
}
