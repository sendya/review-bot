package net.uncrash.reviewbot.bot.commands;

import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.annotation.BotCommand;
import net.uncrash.reviewbot.api.Command;
import net.uncrash.reviewbot.bot.domain.ReviewMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@BotCommand(command = "/hello")
public class HelloCommand implements Command {

    @Override
    public boolean accept(Update update, TelegramLongPollingBot bot) {

        Message message = update.getMessage();
        try {

            SendMessage sendMessage = new SendMessage();
            InlineKeyboardMarkup replyKeyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();

            // reviewMessage 从数据库中取
            // ReviewMessageService.findByChatId(message.getChatId())
            ReviewMessage reviewMessage = new ReviewMessage();
            reviewMessage.getReviewBtn().forEach(item -> {
                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(btn(item.getTitle(), item.getValue()));
                buttonList.add(row);
            });

            List<InlineKeyboardButton> controlBtnGroup = new ArrayList<>();
            reviewMessage.getControlBtn().forEach(item -> {
                controlBtnGroup.add(btn(item.getTitle(), item.getValue()));
            });
            buttonList.add(controlBtnGroup);

            replyKeyboard.setKeyboard(buttonList);

            sendMessage.setReplyMarkup(replyKeyboard);
            sendMessage.setText(reviewMessage.getQuestion());
            sendMessage.setChatId(message.getChatId());
            bot.execute(sendMessage);
            Thread.sleep(5000);
            log.info("HelloCommand accepted.");
        } catch (Exception e) {

        }
        return true;
    }

    private static InlineKeyboardButton btn(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text).setCallbackData(callbackData);
        return button;
    }
}
