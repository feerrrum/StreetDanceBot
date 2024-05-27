package org.bot.commands;

import org.bot.users.UserSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    private final List<Command> commands = new ArrayList<>();
    private static final Map<String, List<CommandResult>> prevCommand = new HashMap<>();

    public void addCommand(Command command) {
        commands.add(command);
    }
    public List<CommandResult> processCommand(UserSession session, String text) {
        var command = commands.stream().filter(c -> c.canBeApply(session, text)).findFirst();
        var result = command.map(c -> {
            try {
                var res = c.execute(session, text);
                prevCommand.put(session.getId(), res);
                return res;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return result.orElse(prevCommand.get(session.getId()));
    }
}
