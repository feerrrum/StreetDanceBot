package org.bot;

import org.bot.commands.CommandHandler;
import org.bot.commands.admin.*;
import org.bot.commands.admin.editAdmins.*;
import org.bot.commands.admin.editCoaches.*;
import org.bot.commands.admin.editCoaches.add.AddCoachInfoCommand;
import org.bot.commands.admin.editCoaches.add.AddCoachNickCommand;
import org.bot.commands.admin.editCoaches.add.AddCoachScheduleCommand;
import org.bot.commands.admin.editCoaches.add.AskForNewCoachNickCommand;
import org.bot.commands.admin.messaging.ConfirmMessageCommand;
import org.bot.commands.admin.messaging.SelectMessageCommand;
import org.bot.commands.admin.messaging.SelectReceiversCommand;
import org.bot.commands.admin.messaging.SendMessagesCommand;
import org.bot.commands.user.*;
import org.bot.commands.user.editCoaches.*;
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
    textHandler.addCommand(new ConfirmMessageCommand());
    textHandler.addCommand(new UpdateScheduleCommand());
    textHandler.addCommand(new AddCoachNickCommand());
    textHandler.addCommand(new AddCoachScheduleCommand());
    textHandler.addCommand(new AddCoachInfoCommand());

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

    buttonHandler.addCommand(new SelectReceiversCommand());
    buttonHandler.addCommand(new SelectMessageCommand());
    buttonHandler.addCommand(new SendMessagesCommand());

    buttonHandler.addCommand(new EditCoachesCommand());
    buttonHandler.addCommand(new ShowCoachesCommand());
    buttonHandler.addCommand(new ShowCoachesForScheduleChangeCommand());
    buttonHandler.addCommand(new ChangeScheduleCommand());
    buttonHandler.addCommand(new AskForNewCoachNickCommand());
    buttonHandler.addCommand(new AddCoachNickCommand());
    buttonHandler.addCommand(new ShowCoachesForDeletionAdminCommand());
    buttonHandler.addCommand(new AdminDeleteCoachCommand());

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
