package com.jaxer.example.command;

/**
 * 命令1
 *
 * @author jaxer
 * date 08/04/2018
 */
public class BuyStock implements Order {

    private Stock stock;

    public BuyStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }
}
