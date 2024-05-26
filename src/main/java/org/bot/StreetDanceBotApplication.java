package org.bot;

import org.bot.commands.CommandHandler;
import org.bot.commands.user.notOnRecordCommand;
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
    textHandler.addCommand(new notOnRecordCommand());
    bot.setTextHandler(textHandler);

    telegramBotsApi.registerBot(bot);
    }
}
