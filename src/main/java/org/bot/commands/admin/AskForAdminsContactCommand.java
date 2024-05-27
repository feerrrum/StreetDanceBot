package org.bot.commands.admin;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.sql.SQLException;
import java.util.List;

public class AskForAdminsContactCommand implements Command {
    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminAddAdmins".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.ADD_ADMIN);
        return List.of(new CommandResult(
                "Поделитесь контактом человека, которого хотите сделать админом:",
                new Button("Назад", "AdminEditAdmins")));
    }
}
