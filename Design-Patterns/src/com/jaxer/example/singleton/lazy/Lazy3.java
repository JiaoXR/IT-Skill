package com.jaxer.example.singleton.lazy3;

/**
 * 同步代码块下的懒汉 - 不可用
 * <p>
 * 优点：不需要在每次调用时加锁，效率比上一个高
 * 缺点：虽然使用了 synchronized，但本质上是线程不安全的。
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Singleton3 {
    private static Singleton3 uniqueInstance;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (uniqueInstance == null) {
            synchronized (Singleton3.class) {
                uniqueInstance = new Singleton3();
            }
        }
        return uniqueInstance;
    }
}
