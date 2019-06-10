package net.uncrash.reviewbot.bot.commands;

import net.uncrash.reviewbot.api.ProcessCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class JoinHandler implements ProcessCommand {

    @Override
    public void join() {

    }

    @Override
    public void leave() {

    }

    @Override
    public boolean accept(Message message, Update update, TelegramLongPollingBot bot) {
        return false;
    }
}
