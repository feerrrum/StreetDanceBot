package org.bot.commands.admin.editCoaches.add;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class AddCoachNickCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return (UserState.ADD_NICK.equals(session.getState()) || "AdminAddCoachNick".equals(text))
                && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setNick(text);
        session.setState(UserState.ADD_SCHEDULE);

        return List.of(new CommandResult(
                "Введите расписание нового тренера",
                new Button("Назад", "AdminAddCoach")));
    }
}
