package net.uncrash.reviewbot.bot.commands;

import net.uncrash.reviewbot.annotation.BotCommand;
import net.uncrash.reviewbot.api.Command;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@BotCommand(command = "/test")
public class TestCommand implements Command {

    @Override
    public boolean accept(Message message, Update update, TelegramLongPollingBot bot) {
        return false;
    }
}
