package org.bot.commands;

import org.bot.users.UserSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private final List<Command> commands = new ArrayList<>();
    private final List<CommandResult> error = List.of(new CommandResult("Я не понимаю :("));
    public void addCommand(Command command) {
        commands.add(command);
    }
    public List<CommandResult> processCommand(UserSession session, String text) {
        var command = commands.stream().filter(c -> c.canBeApply(session, text)).findFirst();
        var result = command.map(c -> {
            try {
                return c.execute(session, text);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return result.orElse(error);
    }
}
