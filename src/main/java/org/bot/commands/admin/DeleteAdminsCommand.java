package org.bot.commands.admin;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.telegram.TelegramUserId;
import org.bot.users.UserProvider;
import org.bot.users.UserSession;
import org.bot.users.UserState;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DeleteAdminsCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public DeleteAdminsCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.DELETE_ADMIN.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        dbHandler.deleteAdmin(text);

        session.setState(UserState.ADMIN);
        var delAdmSession = UserProvider.getInstance().findUserById(new TelegramUserId(text));
        delAdmSession.setState(UserState.NOT_ON_RECORD);
        delAdmSession.delAdmin();
        return List.of(new CommandResult(
                "Админ был удален!\n\nЧто хотите сделать?",
                ButtonHelper.adminEditAdminsButtons));
    }
}
