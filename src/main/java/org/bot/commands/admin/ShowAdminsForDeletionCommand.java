package org.bot.commands.admin;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowAdminsForDeletionCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowAdminsForDeletionCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminAddAdmins".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var admins = new java.util.ArrayList<>(
                dbHandler.getAdminsForDeletion(session.getId()).stream()
                        .map(n -> List.of(new Button(n.get(0) + " " + n.get(1), n.get(1))))
                        .toList());
        admins.add(List.of(new Button("Назад", "AdminEditAdmins")));

        session.setState(UserState.DELETE_ADMIN);

        return List.of(new CommandResult("Кого?", admins));
    }
}
