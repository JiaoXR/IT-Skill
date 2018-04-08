package com.jaxer.example.command;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令调用者
 *
 * @author jaxer
 * date 08/04/2018
 */
public class Broker {
    private List<Order> orderList = new ArrayList<>();

    public void takeOrder(Order order) {
        orderList.add(order);
    }

    public void placeOrders() {
        for (Order order : orderList) {
            order.execute();
        }
        orderList.clear();
    }
}
