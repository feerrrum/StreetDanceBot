package org.bot.commands;

import java.util.List;

public interface ButtonHelper {
    List<List<Button>> admMenuButtons = List.of(List.of(
            new Button("Рассылка", "AdmMenuToMailing"),
            new Button("Тренеры", "AdmMenuToCoaches"),
            new Button("Админы", "AdmMenuToAdmins"),
            new Button("Продолжить как пользователь", "AdmMenuToUsers")));
}
