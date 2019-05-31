package net.uncrash.reviewbot.bot;

import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.bot.config.BotConfig;
import net.uncrash.reviewbot.handler.CommandHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Sendya
 */
@Slf4j(topic = "SimpleBotReceived")
@Component
public class SimpleBot extends TelegramLongPollingBot {

    private static final int TIMEOUT = 60 * 1000;

    private final BotConfig botConfig;
    private final CommandHub commandHub;

    @Autowired
    public SimpleBot(BotConfig botConfig, CommandHub commandHub) {
        super(botConfig.getDefaultBotOption());
        this.botConfig = botConfig;
        this.commandHub = commandHub;
    }

    /**
     * 收到 Telegram 消息
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        // if (update.getMessage().getDate())
        commandHub.handler(update, this);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
