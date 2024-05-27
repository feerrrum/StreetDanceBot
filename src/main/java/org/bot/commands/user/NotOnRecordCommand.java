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

public class NotOnRecordCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public NotOnRecordCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        if (!UserState.NOT_ON_RECORD.equals(session.getState()) && !"UserMenu".equals(text)) return false;
        session.setState(UserState.NOT_ON_RECORD);

        try {
            if (dbHandler.isOnRecord(session.getId())) {
                session.setState(UserState.USER);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return UserState.NOT_ON_RECORD.equals(session.getState());
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        var result = new java.util.ArrayList<>(dbHandler.showCoaches().stream().map(CommandResult::new).toList());
        var nicks = dbHandler
                .getNicks(session.getId())
                .stream().map(n -> List.of(new Button(n, n)))
                .toList();
        result.add(new CommandResult("К кому хотите записаться?", nicks));

        session.setState(UserState.USR_ADD_COACH);

        return result;
    }
}
