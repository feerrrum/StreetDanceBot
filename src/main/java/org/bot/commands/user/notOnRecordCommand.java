package org.bot.commands.user;

import org.bot.commands.Button;
import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class notOnRecordCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public notOnRecordCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.NOT_ON_RECORD.equals(session.getState());
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        if (dbHandler.isAdmin(session.getId())) {
            session.setAdmin();
            session.setState(UserState.ADMIN);
            return List.of(new CommandResult("Что хотите сделать?", ButtonHelper.admMenuButtons));
        }

        session.setState(UserState.USER);
        var result = new java.util.ArrayList<>(dbHandler.getCards().stream().map(CommandResult::new).toList());
        var nicks = dbHandler.getNicks().stream().map(n -> List.of(new Button(n, n))).toList();
        result.add(new CommandResult("К кому хотите записаться?", nicks));

        return result;
    }
}
