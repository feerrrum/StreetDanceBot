package org.bot.users;


public interface UserSession {
    String getId();
    UserState getState();
    boolean isAdmin();

    void setState(UserState state);
    void setAdmin();
}
