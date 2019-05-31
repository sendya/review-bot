package net.uncrash.reviewbot.handler;

import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.api.Command;
import net.uncrash.reviewbot.api.ProcessCommand;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sendya
 */
@Slf4j(topic = "CommandHub")
@Component
public class CommandHub {

    private static final Map<String, Command>        commands        = new ConcurrentHashMap<>();
    private static final Map<String, ProcessCommand> processCommands = new ConcurrentHashMap<>(8);

    public void register(String key, Command command) {
        commands.put(key, command);
        log.info("Hub.register :\t{}", command);
    }

    public void register(String[] key, Command command) {
        if (key.length == 1) {
            this.register(key[0], command);
            return;
        }
        for (String str : key) {
            this.register(str, command);
        }
    }

    public void unregister(String key) {
        commands.remove(key);
    }

    @Async
    public void handler(Update update, TelegramLongPollingBot bot) {
        log.debug("Execute method asynchronously {}", Thread.currentThread().getName());
        log.info("Update: {}", update);
        if (!update.hasMessage()) {

            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                log.info("CallbackQuery: {}", callbackQuery);
                return;
            }
            // join, leave message
            Message message = update.getMessage();
            if (message.getNewChatMembers() != null && message.getNewChatMembers().size() > 0) {
                // TODO exec join handle
                return;
            }
            if (message.getLeftChatMember() != null && message.getLeftChatMember().getId() > 0) {
                // TODO exec leave handle
                return;
            }
        }

        String text = update.getMessage().getText();
        if (text.startsWith("/")) {
            String[] arr = text.split("@");
            if (arr.length > 0) {
                String commandKey = arr[0].trim();
                String[] k = commandKey.split(" ");
                log.debug("command key: {}, k： {}", commandKey, k);
                Command command = commands.get(k[0]);
                if (command == null) {
                    log.info("command not found.");
                    return;
                }
                command.accept(update, bot);
            }
        }
    }
}