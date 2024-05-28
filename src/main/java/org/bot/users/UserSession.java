package org.bot.users;


public interface UserSession {
    String getId();
    UserState getState();
    boolean isAdmin();
    String getReceiversCoach();
    String getMessage();
    String getChangedCoach();
    String getNick();
    String getSchedule();

    void setState(UserState state);
    void setAdmin();
    void delAdmin();
    void setMessage(String message);
    void setReceiversCoach(String coach);
    void setChangedCoach(String coach);
    void setNick(String nick);
    void setSchedule(String schedule);
}
