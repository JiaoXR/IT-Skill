package com.jaxer.example.command;

/**
 * 测试类
 *
 * @author jaxer
 * date 08/04/2018
 */
public class TestCommand {
    public static void main(String[] args) {
        Stock stock = new Stock();

        BuyStock buyStock = new BuyStock(stock);
        SellStock sellStock = new SellStock(stock);

        Broker broker = new Broker();
        broker.takeOrder(buyStock);
        broker.takeOrder(sellStock);

        broker.placeOrders();
    }
}
