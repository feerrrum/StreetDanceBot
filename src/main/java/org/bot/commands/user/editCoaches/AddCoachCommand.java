package org.bot.commands.user.editCoaches;

import org.bot.commands.Button;
import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddCoachCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public AddCoachCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.USR_ADD_COACH.equals(session.getState());
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        dbHandler.userAddCoach(session.getId(), text);
        session.setState(UserState.USER);
        var buttons = new ArrayList<>(ButtonHelper.userMenuButtons);
        if (session.isAdmin()) buttons.add(List.of(new Button("Продолжить как админ", "AdminMenu")));

        return List.of(new CommandResult(
                "Поздравляю! \nВы записаны :) \n\nЧто хотите сделать дальше?",
                buttons));
    }
}
