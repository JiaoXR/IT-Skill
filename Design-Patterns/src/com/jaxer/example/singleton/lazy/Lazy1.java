package com.jaxer.example.singleton.lazy;

/**
 * 懒汉式-1
 * <p>
 * 缺点：只有在单线程下能保证只有一个实例，多线程下可能会创建多个实例。
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Lazy1 {

    private static Lazy1 uniqueInstance;

    private Lazy1() {
    }

    public static Lazy1 getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Lazy1();
        }
        return uniqueInstance;
    }

}
