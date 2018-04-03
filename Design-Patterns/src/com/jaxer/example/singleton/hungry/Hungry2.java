package com.jaxer.example.singleton.hungry;

/**
 * 静态代码块 饿汉 可用
 * <p>
 * 优点：无线程同步问题
 * 缺点：类装载时创建实例，无Lazy Loading。实例一直未被使用时，会浪费资源
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Hungry2 {
    private static Hungry2 uniqueInstance;

    static {
        uniqueInstance = new Hungry2();
    }

    private Hungry2() {
    }

    public static Hungry2 getInstance() {
        return uniqueInstance;
    }
}
