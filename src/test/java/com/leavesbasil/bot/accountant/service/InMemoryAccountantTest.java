package com.leavesbasil.bot.accountant.service;

import org.junit.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class InMemoryAccountantTest {

    @Test
    public void testJoin() {
        User user = new User();
        Accountant sut = new InMemoryAccountant();
        sut.registerUser(user);
        assertEquals(1, sut.getUsers().size());
    }

    @Test
    public void testAddPurchase() {
        User user = new User();
        Accountant sut = new InMemoryAccountant();
        sut.registerUser(user);
        sut.addPurchase(user, "Tomato", BigDecimal.valueOf(25.5));
        assertEquals(1, sut.getPurchases().size());
    }
}