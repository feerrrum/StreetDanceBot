package org.bot.commands.admin.editCoaches;

import org.bot.commands.Button;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowCoachesForScheduleChangeCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public ShowCoachesForScheduleChangeCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminChangeSchedule".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        session.setState(UserState.SELECTING_COACH_FOR_CHANGES);
        var coaches = new ArrayList<>(dbHandler
                .getNicks()
                .stream().map(n -> List.of(new Button(n, n)))
                .toList());
        coaches.add(List.of(new Button("Назад", "AdminEditCoaches")));

        return List.of(new CommandResult("Чье расписание хотите изменить?", coaches));
    }
}
