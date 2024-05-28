package org.bot.commands.admin.messaging;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class ConfirmMessageCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.SELECTING_MESSAGE.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.ADMIN);
        session.setMessage(text);

        String response = String.format(
                "Сообщение:\n\n%s\n\nбудет разослано людям, занимающимся у тренера:\n\n%s",
                session.getMessage(), session.getReceiversCoach());

        if ("MessageToAllUsers".equals(session.getReceiversCoach())) {
            response = String.format(
                    "Сообщение:\n\n%s\n\nбудет разослано всем",
                    session.getMessage());
        }

        return List.of(
                new CommandResult(response, List.of(
                        List.of(new Button("Подтвердить", "AdminConfirmMessage")),
                        List.of(new Button("Назад", "AdminSelectMessage")))));
    }
}
