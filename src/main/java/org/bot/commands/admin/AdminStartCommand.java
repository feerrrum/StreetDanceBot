package org.bot.commands.admin;

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

public class AdminStartCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public AdminStartCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        if (!UserState.NOT_ON_RECORD.equals(session.getState())) return false;

        try {
            if (dbHandler.isAdmin(session.getId())) {
                session.setAdmin();
                ButtonHelper.userMenuButtons.add(
                        List.of(new Button("Продолжить как админ", "ContinueAsAdmin")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return UserState.NOT_ON_RECORD.equals(session.getState()) && session.isAdmin();
    }

        @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.ADMIN);
        return List.of(new CommandResult("Продолжить сессию как:", ButtonHelper.adminStartButtons));
    }
}
