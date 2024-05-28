package org.bot.commands.admin.messaging;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SelectReceiversCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public SelectReceiversCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminSelectReceivers".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var buttons = new java.util.ArrayList<>(dbHandler
                .getNicks().stream()
                .map(coach -> List.of(new Button(coach, coach)))
                .toList());
        buttons.add(List.of(new Button("Разослать всем", "MessageToAllUsers")));
        buttons.add(List.of(new Button("Назад", "AdminMenu")));

        session.setState(UserState.SELECTING_RECEIVERS);
        return List.of(new CommandResult("Выберите тренера, чьи ученики получат сообщение", buttons));
    }
}
