package org.bot.commands;

public class Button {
    private final String text;
    private final String callback;

    public Button(String text, String callback) {
        this.text = text;
        this.callback = callback;
    }

    public String getText() {
        return this.text;
    }

    public String getCallback() {
        return callback;
    }
}
