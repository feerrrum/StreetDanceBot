package org.bot.commands.admin.editCoaches;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class ChangeScheduleCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.SELECTING_COACH_FOR_CHANGES.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.CHANGING_SCHEDULE);
        session.setChangedCoach(text);

        return List.of(
                new CommandResult(String.format("Введите новое расписание для %s", text),
                        new Button("Назад", "AdminChangeSchedule")));
    }
}
