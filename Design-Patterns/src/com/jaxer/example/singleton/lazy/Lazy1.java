package com.jaxer.example.singleton.lazy1;

/**
 * 懒汉式-1
 * <p>
 * 缺点：只有在单线程下能保证只有一个实例，多线程下可能会创建多个实例。
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Singleton1 {

    private static Singleton1 uniqueInstance;

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton1();
        }
        return uniqueInstance;
    }

}
