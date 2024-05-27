package org.bot.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private final Statement stmt;
    private static DatabaseHandler instance;

    private DatabaseHandler() throws IOException, SQLException {
        var data = Files.readAllLines(new File("src\\main\\resources\\dbData").toPath());
        var url = data.get(0);
        var user = data.get(1);
        var password = data.get(2);

        Connection conn = DriverManager.getConnection(url, user, password);
        this.stmt = conn.createStatement();
        System.out.println("Connected successfully");
    }

    public static DatabaseHandler getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public boolean isOnRecord(String id) throws SQLException {
        var exist = stmt.executeQuery("SELECT EXISTS" +
                "(SELECT * FROM users WHERE tg_id=" + id + ")");
        exist.next();
        return exist.getInt(1) != 0;
    }

    public boolean isAdmin(String id)  throws SQLException {
        var exist = stmt.executeQuery("SELECT EXISTS" +
                "(SELECT * FROM admins WHERE tg_id=" + id + ")");
        exist.next();
        return exist.getInt(1) != 0;
    }

    public void userAddCoach(String userId, String coachNick) throws SQLException {
        if (getUsersCoaches(userId).contains(coachNick)) return;

        stmt.executeUpdate(
                String.format("INSERT INTO users (tg_id, coach_nick) VALUES (\"%s\", \"%s\")", userId, coachNick));
    }

    public void userDeleteCoach(String userId, String coachNick) throws SQLException {
        if (!getUsersCoaches(userId).contains(coachNick)) return;

        stmt.executeUpdate(String.format("DELETE FROM users WHERE tg_id=\"%s\" AND coach_nick=\"%s\"", userId, coachNick));
    }

    public List<String> getCards() throws SQLException {
        return getCoaches()
                .stream()
                .map(c -> String.format("%s\n\nРасписание:\n\n%s\n\n%s", c.nick(), c.schedule(), c.info()))
                .toList();
    }

    public List<String> getNicks(String id) throws SQLException {
        var usersCoaches = getUsersCoaches(id);
        return getCoaches().stream().map(Coach::nick)
                .filter(nick -> !usersCoaches.contains(nick)).toList();
    }

    public String getSchedule(String id) throws SQLException {
        var usersCoaches = getUsersCoaches(id);
        return getCoaches().stream()
                .filter(c -> usersCoaches.contains(c.nick()))
                .map(c -> c.nick() + ":\n" + c.schedule())
                .reduce((x,y) -> x + "\n\n" + y)
                .orElse("Вы ни к кому не записаны\n");
    }

    public List<String> getUsersCoaches(String userId) throws SQLException {
        List<String> coaches = new ArrayList<>();
        var rs = stmt.executeQuery(String.format("SELECT * FROM users WHERE tg_id=\"%s\"", userId));
        while (rs.next()) {
            coaches.add(rs.getString("coach_nick"));
        }
        return coaches;
    }

    private List<Coach> getCoaches() throws SQLException {
        List<Coach> coaches = new ArrayList<>();
        var rs = stmt.executeQuery("SELECT * FROM coaches");
        while (rs.next()) {
            coaches.add(new Coach(
                    rs.getString("nick"),
                    rs.getString("sched"),
                    rs.getString("info")));
        }
        return coaches;
    }
}