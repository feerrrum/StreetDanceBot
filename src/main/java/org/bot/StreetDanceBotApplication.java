package org.bot;

import org.bot.commands.CommandHandler;
import org.bot.commands.admin.*;
import org.bot.commands.user.*;
import org.bot.telegram.StreetDanceBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class StreetDanceBotApplication {
    public static void main(String[] args) throws TelegramApiException, IOException, SQLException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    var data = Files.readAllLines(new File("src\\main\\resources\\botData").toPath());
    String token = data.get(0);
    String name = data.get(1);
    var bot = new StreetDanceBot(token, name);

    CommandHandler textHandler = new CommandHandler();

    textHandler.addCommand(new AdminStartCommand());
    textHandler.addCommand(new NotOnRecordCommand());
    textHandler.addCommand(new AddAdminCommand());

    bot.setTextHandler(textHandler);

    CommandHandler buttonHandler = new CommandHandler();

    buttonHandler.addCommand(new AdminStartCommand());
    buttonHandler.addCommand(new NotOnRecordCommand());

    buttonHandler.addCommand(new AdminMenuCommand());
    buttonHandler.addCommand(new EditAdminsCommand());
    buttonHandler.addCommand(new ShowAdminsCommand());
    buttonHandler.addCommand(new AskForAdminsContactCommand());
    buttonHandler.addCommand(new ShowAdminsForDeletionCommand());
    buttonHandler.addCommand(new DeleteAdminsCommand());

    buttonHandler.addCommand(new UserMenuCommand());
    buttonHandler.addCommand(new ShowScheduleCommand());
    buttonHandler.addCommand(new EditCommand());
    buttonHandler.addCommand(new ShowCoachesForAdditionCommand());
    buttonHandler.addCommand(new ShowCoachesForDeletionCommand());
    buttonHandler.addCommand(new AddCoachCommand());
    buttonHandler.addCommand(new DeleteCoachCommand());

    bot.setButtonHandler(buttonHandler);

    telegramBotsApi.registerBot(bot);
    }
}
