package org.bot.telegram;

import org.bot.users.UserId;

import java.util.Objects;


public record TelegramUserId(String id) implements UserId {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUserId that = (TelegramUserId) o;
        return Objects.equals(id, that.id);
    }

}