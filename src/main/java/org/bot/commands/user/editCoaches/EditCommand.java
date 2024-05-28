package org.bot.commands.user.editCoaches;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public EditCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "UserEdit".equals(text);
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.USER);
        return List.of(new CommandResult("Что хотите сделать?", ButtonHelper.userEditButtons));
    }
}
