package net.uncrash.reviewbot.api;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    boolean accept(Update update, TelegramLongPollingBot bot);

}