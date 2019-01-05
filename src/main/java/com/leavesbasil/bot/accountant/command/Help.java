package com.leavesbasil.bot.accountant.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;

@Component
public class Help extends HelpCommand {

    private static final String COMMAND_IDENTIFIER = "help";
    private static final String COMMAND_DESCRIPTION = "shows all commands. Use /help [command] for more info";
    private static final String EXTENDED_DESCRIPTION = "This command displays all commands the bot has to offer.\n /help [command] can display deeper information";

    public Help() {
        super(COMMAND_IDENTIFIER, COMMAND_DESCRIPTION, EXTENDED_DESCRIPTION);
    }
}
