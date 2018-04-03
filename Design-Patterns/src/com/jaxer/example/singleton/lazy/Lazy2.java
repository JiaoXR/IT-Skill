package com.jaxer.example.singleton.lazy2;

/**
 * 同步方法下的懒汉 - 可用，不推荐
 * <p>
 * 优点：线程安全，可确保正常使用下（不考虑通过反射调用私有构造方法）只有一个实例
 * 缺点：每次获取实例都需要申请锁，开销大，效率低
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Singleton2 {
    private static Singleton2 uniqueInstance;

    private Singleton2() {
    }

    public static synchronized Singleton2 getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton2();
        }
        return uniqueInstance;
    }
}
