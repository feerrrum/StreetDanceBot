package org.bot.users;


public interface UserSession {
    String getId();
    UserState getState();
    boolean isAdmin();
    String getReceiversCoach();
    String getMessage();

    void setState(UserState state);
    void setAdmin();
    void delAdmin();
    void setMessage(String message);
    void setReceiversCoach(String coach);
}
