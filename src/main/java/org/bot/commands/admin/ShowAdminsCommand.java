package org.bot.commands.admin;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowAdminsCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowAdminsCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminShowAdmins".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        return List.of(new CommandResult(dbHandler.showAdmins(), new Button("Назад", "AdminMenu")));
    }
}
