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

    public List<String> showCoaches() throws SQLException {
        return getCoaches()
                .stream()
                .map(c -> String.format("%s\n\nРасписание:\n\n%s\n\nИнфа:\n\n%s", c.nick(), c.schedule(), c.info()))
                .toList();
    }

    public List<String> getNicks() throws SQLException {
        return getCoaches().stream().map(Coach::nick).toList();
    }

    public String getSchedule(String id) throws SQLException {
        var usersCoaches = getUsersCoaches(id);
        return getCoaches().stream()
                .filter(c -> usersCoaches.contains(c.nick()))
                .map(c -> c.nick() + ":\n" + c.schedule())
                .reduce((acc, cur) -> acc + "\n\n" + cur)
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

    public String showAdmins() throws SQLException {
        return getAdmins().stream()
                .map(adm -> String.format(
                        "tg_id: %s\nname: %s\nphone_number: %s\naccess_lvl: %d",
                        adm.id(), adm.name(), adm.phoneNumber(), adm.accessLvl()))
                .reduce((acc, cur) -> acc + "\n\n" +  cur)
                .orElse("Пусто");
    }

    public List<List<String>> getAdminsForDeletion(String admId) throws SQLException {
        int admLvl = getLevel(admId);
        return getAdmins().stream()
                .filter(adm -> adm.accessLvl() > admLvl)
                .map(adm -> List.of(adm.id(), adm.name(), adm.phoneNumber()))
                .toList();
    }

    public void addAdmin(String admId, String newAdmId, String name, String phoneNumber) throws SQLException {
        stmt.executeUpdate(String.format(
                "INSERT INTO admins (tg_id, admin_name, phone_number, access_lvl) VALUES (\"%s\", \"%s\", \"%s\", %d)",
                newAdmId, name, phoneNumber, getLevel(admId) + 1));
    }

    public void deleteAdmin(String admId) throws SQLException {
        stmt.executeUpdate("DELETE FROM admins WHERE tg_id=" + admId);
    }

    public List<String> getUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        var rs = stmt.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            users.add(rs.getString("tg_id"));
        }
        return users.stream().distinct().toList();
    }
    public List<String> getCoachesUsers(String nick) throws SQLException {
        List<String> users = new ArrayList<>();
        var rs = stmt.executeQuery(String.format("SELECT * FROM users WHERE coach_nick=\"%s\"", nick));
        while (rs.next()) {
            users.add(rs.getString("tg_id"));
        }
        return users;

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

    private List<Admin> getAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        var rs = stmt.executeQuery("SELECT * FROM admins");
        while (rs.next()) {
            admins.add(new Admin(
                    rs.getString("tg_id"),
                    rs.getString("admin_name"),
                    rs.getString("phone_number"),
                    rs.getInt("access_lvl")));
        }
        return admins;
    }

    private int getLevel(String admId) throws SQLException {
        var exist = stmt.executeQuery("SELECT * FROM admins WHERE tg_id=" + admId);
        exist.next();
        return exist.getInt("access_lvl");
    }
}