package net.uncrash.reviewbot.bot.commands;

import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.annotation.BotCommand;
import net.uncrash.reviewbot.api.Command;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@BotCommand(command = "/hello")
public class HelloCommand implements Command {

    @Override
    public boolean accept(Update update, TelegramLongPollingBot bot) {

        try {
            Thread.sleep(5000);
            log.info("HelloCommand accepted.");
        } catch (InterruptedException e) {

        }
        return true;
    }
}
