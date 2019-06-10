package net.uncrash.reviewbot.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.api.Command;
import net.uncrash.reviewbot.api.UpdateType;
import net.uncrash.reviewbot.api.UpdateHandler;
import net.uncrash.reviewbot.registry.CommandHub;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author jjandxa
 */
@Slf4j(topic = "MessageHandler")
@Component(UpdateType.MESSAGE_FIELD)
@RequiredArgsConstructor
public class MessageHandler implements UpdateHandler<Message> {

    private final CommandHub commandHub;

    @Override
    public void handler(Message message, Update update, TelegramLongPollingBot bot) {
        String text = message.getText();
        if (text.startsWith("/")) {
            String[] arr = text.split("@");
            if (arr.length > 0) {
                String commandKey = arr[0].trim();
                String[] k = commandKey.split(" ");
                log.debug("command key: {}, k： {}", commandKey, k);
                Command command = commandHub.getCommands().get(k[0]);
                if (command == null) {
                    log.info("command not found.");
                    return;
                }
                command.accept(message, update, bot);
            }
        }
    }
}