package org.bot.commands.admin;

import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;

import java.sql.SQLException;
import java.util.List;

public class AskForAdminsContactCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return false;
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        return null;
    }
}
