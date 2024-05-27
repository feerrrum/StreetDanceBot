package org.bot.telegram;

import org.bot.commands.Button;
import org.bot.commands.CommandHandler;
import org.bot.commands.CommandResult;
import org.bot.users.UserProvider;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StreetDanceBot extends TelegramLongPollingBot {
    private final String name;
    private CommandHandler textHandler = null;
    private CommandHandler buttonHandler = null;

    public StreetDanceBot(String token, String name){
        super(token);
        this.name = name;
    }
    @Override
    public void onUpdateReceived(Update update) {
        TelegramUpdate tgUpdate = new TelegramUpdate(update);
        var chatId = tgUpdate.getChatId();
        var session = UserProvider.getInstance().findUserById(new TelegramUserId(chatId));

        if ((TelegramType.Text.equals(tgUpdate.getTgType()))) {
            var result = textHandler.processCommand(session, tgUpdate.getData());
            result.forEach(r -> send(r, chatId));
        }
        if (TelegramType.CallbackQuery.equals(tgUpdate.getTgType())) {
            var result = buttonHandler.processCommand(session, tgUpdate.getData());
            if (result.size() > 1) result.forEach(r -> send(r, chatId));
            else result.forEach(r -> edit(r, chatId, tgUpdate.getMessageId()));
        }
    }

    private void send(CommandResult result, String chatId) {
        if (!result.hasChatId()) result.setChatId(chatId);
        SendMessage message = new SendMessage(result.getChatId(), result.getMessage());

        if (result.hasButtons()) message.setReplyMarkup(getInlineKeyBoardMarkup(result));

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void edit(CommandResult result, String chatId, Integer messageId) {
        EditMessageText message = new EditMessageText(result.getMessage());
        message.setChatId(chatId);
        message.setMessageId(messageId);

        if (result.hasButtons()) message.setReplyMarkup(getInlineKeyBoardMarkup(result));

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private InlineKeyboardMarkup getInlineKeyBoardMarkup(CommandResult result) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (List<Button> row : result.getButtons()) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for (Button button : row) {
                InlineKeyboardButton inlineButton = new InlineKeyboardButton(button.text());
                inlineButton.setCallbackData(button.callback());
                rowInline.add(inlineButton);
            }
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public void setTextHandler(CommandHandler textHandler) {
        this.textHandler = textHandler;
    }
    public void setButtonHandler(CommandHandler buttonHandler) {
        this.buttonHandler = buttonHandler;
    }

    @Override
    public String getBotUsername() {
        return name;
    }
}
