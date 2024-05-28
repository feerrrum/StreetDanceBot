package org.bot.commands.admin.editCoaches;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminDeleteCoachCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public AdminDeleteCoachCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.ADMIN_DELETE_COACH.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        dbHandler.adminDeleteCoach(text);
        session.setState(UserState.ADMIN);
        return List.of(new CommandResult(
                "Тренер удален\n\nЧто хотите сделать дальше?", ButtonHelper.adminEditCoachesButtons));
    }
}
