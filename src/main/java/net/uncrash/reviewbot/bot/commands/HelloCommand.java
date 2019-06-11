package net.uncrash.reviewbot.bot.commands;

import lombok.extern.slf4j.Slf4j;
import net.uncrash.reviewbot.annotation.BotCommand;
import net.uncrash.reviewbot.api.Command;
import net.uncrash.reviewbot.bot.domain.Button;
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
    public boolean accept(Message message, Update update, TelegramLongPollingBot bot) {

        try {

            SendMessage sendMessage = new SendMessage();
            InlineKeyboardMarkup replyKeyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();

            // reviewMessage 从数据库中取
            // ReviewMessageService.findByChatId(message.getChatId())
            ReviewMessage reviewMessage = new ReviewMessage();
            // mock data
            List<Button> buttons = new ArrayList<>();
            buttons.add(Button.builder().title("A. 他家炸了").value("A").build());
            buttons.add(Button.builder().title("B. 剧透死全家").value("B").build());
            buttons.add(Button.builder().title("C. 引战").value("C").build());
            buttons.add(Button.builder().title("D. 不喜欢xx的滚出去").value("D").build());
            reviewMessage.setReviewBtn(buttons);

            List<Button> cButtons = new ArrayList<>();
            cButtons.add(Button.builder().title("⭕️ 人工通过").value("pass").build());
            cButtons.add(Button.builder().title("❌ 送进小黑屋").value("block").build());
            reviewMessage.setControlBtn(cButtons);

            reviewMessage.setQuestion("欢迎加入群 `XXX`，本群设置了需要验证用户来源，请回答以下问题：\r\n" +
                    "以下哪个是正确的举报理由？\r\n\r\n" +
                    "回答完毕后才能解除群正常功能");
            // mock end
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
            log.error("HelloCommand Error: {}", e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    private static InlineKeyboardButton btn(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text).setCallbackData(callbackData);
        return button;
    }
}
