package org.bot.commands.admin;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class AdminMenuCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminMenu".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.ADMIN);
        return List.of(new CommandResult("Что хотите сделать?", ButtonHelper.adminMenuButtons));
    }
}
