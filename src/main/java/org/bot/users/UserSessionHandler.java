package org.bot.users;


public class UserSessionHandler implements UserSession{
    private final UserId id;
    private UserState state = UserState.NOT_ON_RECORD;
    private boolean isAdmin = false;
    private String receiversCoach;
    private String message;
    private String changesCoach;
    private String coachNick;
    private String coachSchedule;

    UserSessionHandler(UserId id) {
        this.id = id;
    }
    @Override
    public String getId() {
        return String.valueOf(id.id());
    }
    @Override
    public UserState getState() {
        return state;
    }
    @Override
    public boolean isAdmin(){
        return isAdmin;
    }

    @Override
    public String getReceiversCoach() {
        return receiversCoach;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getChangedCoach() {
        return changesCoach;
    }

    @Override
    public String getNick() {
        return coachNick;
    }

    @Override
    public String getSchedule() {
        return coachSchedule;
    }


    @Override
    public void setState(UserState state) {
        this.state = state;
    }
    @Override
    public void setAdmin() {
        isAdmin = true;
    }

    @Override
    public void delAdmin() {
        isAdmin = false;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setReceiversCoach(String coach) {
        receiversCoach = coach;
    }

    @Override
    public void setChangedCoach(String coach) {
        changesCoach = coach;
    }

    @Override
    public void setNick(String nick) {
        coachNick = nick;
    }

    @Override
    public void setSchedule(String schedule) {
        coachSchedule = schedule;
    }
}
