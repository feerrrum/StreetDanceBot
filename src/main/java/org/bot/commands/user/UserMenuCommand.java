package org.bot.commands.user;

import org.bot.commands.Button;
import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMenuCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "UserMenu".equals(text);
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var buttons = new ArrayList<>(ButtonHelper.userMenuButtons);
        if (session.isAdmin()) buttons.add(List.of(new Button("Продолжить как админ", "AdminMenu")));

        session.setState(UserState.USER);
        return List.of(new CommandResult("Что хотите сделать дальше?", buttons));
    }
}
