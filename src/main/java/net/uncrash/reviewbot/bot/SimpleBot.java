package net.uncrash.reviewbot.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.api.UpdateHandler;
import net.uncrash.reviewbot.bot.config.BotConfig;
import net.uncrash.reviewbot.utils.StreamUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Sendya
 */
@Slf4j(topic = "SimpleBotReceived")
@Component
@RequiredArgsConstructor
public class SimpleBot extends TelegramLongPollingBot {

    private static final int TIMEOUT = 60 * 1000;

    private final BotConfig botConfig;

    private final ApplicationContext applicationContext;

    /**
     * Received Telegram Message
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Update: {}", update);
        Field[] fields = update.getClass().getDeclaredFields();
        Stream.of(fields)
                .forEach(StreamUtils.tryCatch(it -> {
                    if (it.get(update) != null
                            && BotApiObject.class.isAssignableFrom(it.get(update).getClass())) {
                        JsonProperty jsonProperty = it.getAnnotation(JsonProperty.class);
                        log.info("update type {}", jsonProperty.value());
                        UpdateHandler updateHandler =
                                (UpdateHandler) applicationContext.getBean(jsonProperty.value());
                        updateHandler.handler((BotApiObject) it.get(update), update, this);
                    }
                }));

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
