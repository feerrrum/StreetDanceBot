package org.bot.commands.admin.editCoaches;

import org.bot.commands.Button;
import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UpdateScheduleCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public UpdateScheduleCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.CHANGING_SCHEDULE.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        dbHandler.setNewScheduleForCoach(session.getChangedCoach(), text);
        session.setState(UserState.ADMIN);
        return List.of(new CommandResult(
                "Расписание обновлено!\n\nЧто хотите сделать дальше?", ButtonHelper.adminEditCoachesButtons));
    }
}
