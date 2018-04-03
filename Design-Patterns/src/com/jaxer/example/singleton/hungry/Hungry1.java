package com.jaxer.example.singleton.hungry;

/**
 * 静态常量 饿汉 - 推荐
 * <p>
 * 优点：实现简单，无线程同步问题
 * 缺点：在类装载时完成实例化。若该实例一直未被使用，则会造成资源浪费
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Hungry1 {
    private static final Hungry1 uniqueInstance = new Hungry1();

    private Hungry1() {
    }

    public static Hungry1 getInstance() {
        return uniqueInstance;
    }
}
