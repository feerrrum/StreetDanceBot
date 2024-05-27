package org.bot.commands.user;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DeleteCoachCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public DeleteCoachCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.USR_DELETE_COACH.equals(session.getState());
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        dbHandler.userDeleteCoach(session.getId(), text);
        session.setState(UserState.USER);
        return List.of(new CommandResult("Успешно", ButtonHelper.userEditButtons));
    }
}
