package com.jaxer.example.command;

/**
 * 命令2
 *
 * @author jaxer
 * date 08/04/2018
 */
public class SellStock implements Order {

    private Stock stock;

    public SellStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}
