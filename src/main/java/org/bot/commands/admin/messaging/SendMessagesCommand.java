package org.bot.commands.admin.messaging;

import org.bot.commands.ButtonHelper;
import org.bot.commands.Command;
import org.bot.commands.CommandResult;
import org.bot.database.DatabaseHandler;
import org.bot.users.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SendMessagesCommand implements Command {
    DatabaseHandler dbHandler = DatabaseHandler.getInstance();

    public SendMessagesCommand() throws SQLException, IOException {
    }

    @Override
    public boolean canBeApply(UserSession session, String text) {
        return "AdminConfirmMessage".equals(text) && session.isAdmin();
    }

    @Override
    public List<CommandResult> execute(UserSession session, String text) throws SQLException {
        List<String> receivers;
        if ("MessageToAllUsers".equals(session.getReceiversCoach())) receivers = dbHandler.getUsers();
        else receivers = dbHandler.getCoachesUsers(session.getReceiversCoach());


        var messages = new ArrayList<>(
                receivers.stream()
                        .map(user -> new CommandResult(session.getMessage()).setChatId(user))
                        .toList());
        var secondMessage = new ArrayList<>(
                receivers.stream()
                        .map(user ->
                                new CommandResult(
                                        "Посмотрите выше, вам пришло сообщение!\n\nЧто хотите сделать?",
                                        ButtonHelper.userMenuButtons)
                                        .setChatId(user))
                        .toList());
        messages.addAll(secondMessage);
        messages.add((new CommandResult(
                "Сообщение отправлено!\n\nЧто хотите сделать?",
                ButtonHelper.adminMenuButtons)));

        return messages;
    }
}
