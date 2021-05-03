package ru.butakov.bash_im_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SendMessageFormat {
    @Value("${bot.button.quote}")
    String commandQuote;
    @Value("${bot.button.strip}")
    String commandStrip;

    public SendMessage getSendMessageBaseFormat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode("HTML");
        sendMessage.disableWebPagePreview();
//        sendMessage.enableWebPagePreview();
        sendMessage.setChatId(String.valueOf(chatId));
        setButtons(sendMessage, commandQuote, commandStrip);
        return sendMessage;
    }

    public SendMessage getSendMessageBaseFormat(long chatId, String text) {
        SendMessage sendMessage = getSendMessageBaseFormat(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public void setButtons(SendMessage sendMessage, List<List<String>> buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        buttons.forEach(l -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            l.forEach(button -> keyboardRow.add(new KeyboardButton(button)));
            keyboard.add(keyboardRow);
        });

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void setButtons(SendMessage sendMessage, String... buttons) {
        List<List<String>> oneLineButtons = new ArrayList<>();
        oneLineButtons.add(Arrays.asList(buttons));
        setButtons(sendMessage, oneLineButtons);
    }
}
