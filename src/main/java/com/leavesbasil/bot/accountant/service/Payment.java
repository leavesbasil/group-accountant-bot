package com.leavesbasil.bot.accountant.service;

import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;

public class Payment {
    private User source;
    private User target;
    private BigDecimal value;

    public Payment(User source, User target, BigDecimal value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
