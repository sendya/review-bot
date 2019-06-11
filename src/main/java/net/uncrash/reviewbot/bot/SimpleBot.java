package net.uncrash.reviewbot.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.api.UpdateHandler;
import net.uncrash.reviewbot.bot.config.BotConfig;
import net.uncrash.reviewbot.utils.StreamUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Sendya
 */
@Slf4j(topic = "SimpleBotReceived")
@Component
public class SimpleBot extends TelegramLongPollingBot {

    private static final int TIMEOUT = 60 * 1000;

    private final BotConfig botConfig;

    private final ApplicationContext applicationContext;

    /**
     * 不能去掉构造器，要用于给 bot 设置代理
     *
     * @param botConfig
     * @param applicationContext
     */
    public SimpleBot(BotConfig botConfig, ApplicationContext applicationContext) {
        super(botConfig.getDefaultBotOption());
        this.botConfig = botConfig;
        this.applicationContext = applicationContext;
    }

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
                .filter(field -> !Modifier.isFinal(field.getModifiers()))
                .filter(field -> BotApiObject.class.isAssignableFrom(field.getType()))
                .forEach(StreamUtils.tryCatch(it -> {
                    it.setAccessible(true);
                    BotApiObject obj = (BotApiObject) it.get(update);
                    if (obj != null) {
                        log.info("field: {}", it);
                        JsonProperty jsonProperty = it.getAnnotation(JsonProperty.class);
                        log.info("update type {}", jsonProperty.value());
                        UpdateHandler updateHandler = (UpdateHandler) applicationContext.getBean(jsonProperty.value());
                        updateHandler.handler(obj, update, this);
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
