package org.bot.commands.admin.editCoaches.add;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddCoachInfoCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public AddCoachInfoCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.ADD_INFO.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.ADMIN);
        dbHandler.adminAddCoach(session.getNick(), session.getSchedule(), text);
        return List.of(new CommandResult(
                "Тренер добавлен!\n\nЧто хотите сделать дальше?", ButtonHelper.adminEditCoachesButtons));
    }
}
