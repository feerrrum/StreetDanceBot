package org.bot.commands;

import java.util.List;

public interface ButtonHelper {
    List<List<Button>> admMenuButtons = List.of(List.of(
            new Button("Рассылка", "AdminMenuToMailing"),
            new Button("Тренеры", "AdminMenuToCoaches"),
            new Button("Админы", "AdminMenuToAdmins")),
            List.of(new Button("Продолжить как пользователь", "AdminMenuToUsers")));
    List<List<Button>> userMenuButtons = List.of(List.of(
            new Button("Расписание", "UserShowSchedule")),
            List.of(new Button("Изменить", "UserEdit")));
    List<List<Button>> userEditButtons = List.of(List.of(
            new Button("Записаться", "UserAddCoach"),
            new Button("Удалить", "UserDeleteCoach")),
            List.of(new Button("Назад", "UserBackToMenu")));
}
