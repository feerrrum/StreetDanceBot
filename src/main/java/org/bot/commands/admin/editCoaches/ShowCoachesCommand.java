package org.bot.commands.admin.editCoaches;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowCoachesCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowCoachesCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminShowCoaches".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var result = new java.util.ArrayList<>(dbHandler.showCoaches().stream().map(CommandResult::new).toList());
        result.add(new CommandResult("Что хотите сдеать?", ButtonHelper.adminEditCoachesButtons));

        return result;
    }
}
