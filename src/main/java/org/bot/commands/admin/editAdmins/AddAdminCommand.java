package org.bot.commands.admin.editAdmins;

import org.bot.commands.Button;
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

public class AddAdminCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public AddAdminCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return UserState.ADD_ADMIN.equals(session.getState()) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        String[] data = text.split(";");
        if (data.length != 3) {
            return List.of(new CommandResult(
                    "Это не контакт",
                    new Button("Назад", "AdminEditAdmins")));
        }

        String newAdmId = data[0];
        String name = data[1];
        String phone_number = data[2];

        if (dbHandler.isAdmin(newAdmId)){
            return List.of(
                    new CommandResult("Уже является админом",
                    new Button("Назад", "AdminEditAdmins")));
        }

        dbHandler.addAdmin(session.getId(), newAdmId, name, phone_number);

        session.setState(UserState.ADMIN);
        var addAdmSession = UserProvider.getInstance().findUserById(new TelegramUserId(newAdmId));
        addAdmSession.setState(UserState.NOT_ON_RECORD);
        addAdmSession.setAdmin();
        return List.of(new CommandResult(
                "Админ был добавлен!\n\nЧто хотите сделать?",
                ButtonHelper.adminEditAdminsButtons));
    }
}
