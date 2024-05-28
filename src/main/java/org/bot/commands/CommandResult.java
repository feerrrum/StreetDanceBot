package org.bot.commands;

import java.util.List;

public class CommandResult {
    private final String message;
    private List<List<Button>> buttons = null;
    private String chatId = null;

    public CommandResult(String message) {
        this.message = message;
    }

    public CommandResult(String message, Button buttons) {
        this.message = message;
        this.buttons = List.of(List.of(buttons));
    }

    public CommandResult(String message, List<List<Button>> buttons) {
        this.message = message;
        this.buttons = buttons;
    }

    public String getMessage() {
        return message;
    }
    public List<List<Button>> getButtons() {
        return buttons;
    }

    public CommandResult setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }
    public String getChatId() {
        return this.chatId;
    }
    public boolean hasChatId() {
        return chatId != null;
    }
    public boolean hasButtons() {
        return buttons != null;
    }
}
