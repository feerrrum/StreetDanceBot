package org.bot.commands.admin.editCoaches.add;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class AddCoachScheduleCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.ADD_SCHEDULE.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setSchedule(text);
        session.setState(UserState.ADD_INFO);

        return List.of(new CommandResult(
                "Введите инфу о новом тренере",
                new Button("Назад", "AdminAddCoachNick")));
    }
}
