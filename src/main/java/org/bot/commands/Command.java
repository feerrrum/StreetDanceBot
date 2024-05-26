package org.bot.commands;

import org.bot.users.UserSession;

import java.sql.SQLException;
import java.util.List;

public interface Command {
    boolean canBeApply(UserSession session, String text);

    List<CommandResult> execute(UserSession session, String text) throws SQLException;
}
