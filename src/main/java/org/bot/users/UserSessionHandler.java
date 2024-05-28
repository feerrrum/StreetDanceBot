package org.bot.users;


public class UserSessionHandler implements UserSession{
    private final UserId id;
    private UserState state = UserState.NOT_ON_RECORD;
    private boolean isAdmin = false;
    private String receiversCoach;
    private String message;

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
    public void setState(UserState state) {
        this.state = state;
    }
    @Override
    public void setAdmin() {
        this.isAdmin = true;
    }

    @Override
    public void delAdmin() {
        this.isAdmin = false;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setReceiversCoach(String coach) {
        this.receiversCoach = coach;
    }
}
