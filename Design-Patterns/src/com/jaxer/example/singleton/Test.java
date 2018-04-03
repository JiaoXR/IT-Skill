package com.jaxer.example.singleton;

import com.jaxer.example.singleton.hungry.Hungry1;
import com.jaxer.example.singleton.hungry.Hungry2;
import com.jaxer.example.singleton.hungry.Hungry3;
import com.jaxer.example.singleton.lazy.Lazy1;
import com.jaxer.example.singleton.lazy.Lazy2;
import com.jaxer.example.singleton.lazy.Lazy3;
import com.jaxer.example.singleton.lazy.Lazy4;

/**
 * 测试
 *
 * @author jaxer
 * date 24/02/2018
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Lazy1.getInstance());
        System.out.println(Lazy2.getInstance());
        System.out.println(Lazy3.getInstance());
        System.out.println(Lazy4.getInstance());
        
        System.out.println(Hungry1.getInstance());
        System.out.println(Hungry2.getInstance());
        System.out.println(Hungry3.getInstance());

        System.out.println(Singleton.getInstance());
    }
}
