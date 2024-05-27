package org.bot.commands.user;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowScheduleCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowScheduleCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "UserShowSchedule".equals(text);
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        return List.of(new CommandResult(
                dbHandler.getSchedule(session.getId()) + "\n\nЧто хотите сделать дальше?",
                ButtonHelper.userMenuButtons));
    }
}
