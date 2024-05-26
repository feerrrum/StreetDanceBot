package org.bot.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramUpdate {
    private TelegramType tgType;
    private String chatId;
    private Integer messageId;
    private String data;

    public TelegramUpdate(Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();
            this.chatId = message.getChatId().toString();

            if (message.hasContact()) {
                this.tgType = TelegramType.Text;

                var contact = message.getContact();
                this.data = String.format("%d %s %s",
                        contact.getUserId(), contact.getPhoneNumber(), contact.getFirstName());
            }
            else if (message.hasText()) {
                this.tgType = TelegramType.Text;
                this.data = message.getText();
            }

        }
        else if (update.hasCallbackQuery()) {
            this.tgType = TelegramType.CallbackQuery;
            var callbackQuery = update.getCallbackQuery();
            this.chatId = callbackQuery.getMessage().getChatId().toString();
            this.messageId = callbackQuery.getMessage().getMessageId();
            this.data = callbackQuery.getData();
        }

    }

    public Integer getMessageId() {
        return messageId;
    }

    public TelegramType getTgType() {
        return tgType;
    }

    public String getChatId() {
        return chatId;
    }

    public String getData() {
        return data;
    }
}
