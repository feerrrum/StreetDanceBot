package org.bot.commands.user.editCoaches;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowCoachesForAdditionCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowCoachesForAdditionCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "UserAddCoach".equals(text);
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var usersCoaches = dbHandler.getUsersCoaches(session.getId());
        var buttons = new java.util.ArrayList<>(dbHandler
                .getNicks().stream()
                .filter(coach -> !usersCoaches.contains(coach))
                .map(coach -> List.of(new Button(coach, coach)))
                .toList());
        buttons.add(List.of(new Button("Назад", "UserEdit")));

        session.setState(UserState.USR_ADD_COACH);

        return List.of(new CommandResult("К кому хотите записаться?", buttons));
    }
}
