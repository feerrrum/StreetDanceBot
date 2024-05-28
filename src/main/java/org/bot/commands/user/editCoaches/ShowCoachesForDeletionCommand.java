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

public class ShowCoachesForDeletionCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowCoachesForDeletionCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "UserDeleteCoach".equals(text);
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var buttons = new java.util.ArrayList<>(dbHandler
                .getUsersCoaches(session.getId()).stream()
                .map(coach -> List.of(new Button(coach, coach)))
                .toList());
            buttons.add(List.of(new Button("Назад", "UserEdit")));

        session.setState(UserState.USR_DELETE_COACH);

        return List.of(new CommandResult("Кого?", buttons));
    }
}
