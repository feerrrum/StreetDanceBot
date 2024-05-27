package org.bot.commands.user;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class BackToMenuCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "UserBackToMenu".equals(text);
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.USER);
        return List.of(new CommandResult("Что хотите сделать дальше?", ButtonHelper.userMenuButtons));
    }
}
