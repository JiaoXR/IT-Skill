package com.jaxer.example.singleton.lazy;

/**
 * 双重检查（Double Check）下的懒汉 - 推荐
 * <p>
 * 优点：使用了双重检查，避免了线程不安全，同时也避免了不必要的锁开销。
 * 缺点：NA
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Lazy4 {
    /**
     * 使用 volatile 关键字的目的不是保证可见性（synchronized 已经保证了可见性），而是为了保证顺序性。
     */
    private static volatile Lazy4 uniqueInstance;

    private Lazy4() {
    }

    public static Lazy4 getInstance() {
        if (uniqueInstance == null) {
            synchronized (Lazy4.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Lazy4();
                }
            }
        }
        return uniqueInstance;
    }
}
