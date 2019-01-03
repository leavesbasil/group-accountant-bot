package com.leavesbasil.bot.accountant.service;

import java.math.BigDecimal;

public class Purchase {
    private String name;
    private BigDecimal price;

    public Purchase(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
