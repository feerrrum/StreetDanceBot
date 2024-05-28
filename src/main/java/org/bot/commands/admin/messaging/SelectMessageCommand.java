package org.bot.commands.admin.messaging;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class SelectMessageCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return (UserState.SELECTING_RECEIVERS.equals(session.getState()) || "AdminSelectMessage".equals(text))
                && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        if (!"AdminSelectMessage".equals(text)) session.setReceiversCoach(text);
        session.setState(UserState.SELECTING_MESSAGE);
        return List.of(
                new CommandResult("Введите сообщение для отправки",
                new Button("Назад", "AdminSelectReceivers")));
    }
}
