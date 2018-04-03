package com.jaxer.example.proxy;

/**
 * 测试类
 * <p>
 * 代理类（MyProxy）与被代理类（CalculatorImpl）要实现同一个接口（ICalculator）；
 * 代理类包含有被代理类的对象，因为干实事的时候还是要靠这个对象的；
 * 代理类可以在被代理类真正“工作”之前和之后做一些必要操作（doSthBefore() 和 doSthAfter()）。
 *
 * 参考：https://www.jianshu.com/p/b0cbdd73b4ce
 *
 * @author jaxer
 * date 26/02/2018
 */
public class TestProxy {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy();
        myProxy.add(1, 2);
    }
}
