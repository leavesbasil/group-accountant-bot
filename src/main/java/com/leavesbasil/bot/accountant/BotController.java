package com.leavesbasil.bot.accountant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    @RequestMapping("/")
    public String index() {
        return "Bot is work!";
    }
}
