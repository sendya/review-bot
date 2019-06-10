package net.uncrash.reviewbot.api;

import org.springframework.scheduling.annotation.Async;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author jjandxa
 */
public interface UpdateHandler<T extends BotApiObject> {

    @Async
    void handler(T message, Update update, TelegramLongPollingBot bot);

}