package org.bot.commands;

import java.util.ArrayList;
import java.util.List;

public class ButtonHelper {
    public static List<List<Button>> userMenuButtons = List.of(
            List.of(new Button("Расписание", "UserShowSchedule")),
            List.of(new Button("Изменить", "UserEdit")));
    public static List<List<Button>> userEditButtons = List.of(
            List.of(
                    new Button("Записаться", "UserAddCoach"),
                    new Button("Удалить", "UserDeleteCoach")),
            List.of(new Button("Назад", "UserMenu")));
    public static List<List<Button>> adminStartButtons = List.of(
            List.of(new Button("Админ", "AdminMenu")),
            List.of(new Button("Пользователь", "UserMenu")));
    public static List<List<Button>> adminMenuButtons = List.of(
            List.of(
                    new Button("Рассылка", "AdminSelectReceivers"),
                    new Button("Тренеры", "AdminEditCoaches"),
                    new Button("Админы", "AdminEditAdmins")),
            List.of(new Button("Продолжить как пользователь", "UserMenu")));
    public static List<List<Button>> adminEditCoachesButtons = List.of(
            List.of(new Button("Посмотреть", "AdminShowCoaches")),
            List.of(new Button("Изменить расписание", "AdminChangeSchedule")),
            List.of(
                    new Button("Добавить", "AdminAddCoach"),
                    new Button("Удалить", "AdminDeleteCoach")),
            List.of(new Button("Назад", "AdminMenu")));
    public static List<List<Button>> adminEditAdminsButtons = List.of(
            List.of(
                    new Button("Посмотреть", "AdminShowAdmins"),
                    new Button("Добавить", "AdminAddAdmins"),
                    new Button("Удалить", "AdminDeleteAdmins")),
            List.of(new Button("Назад", "AdminMenu")));
}
