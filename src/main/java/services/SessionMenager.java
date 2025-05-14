package services;

import models.User;

public class SessionMenager {
    private static SessionMenager instance;

    //Anetaret e Sessionit
    private User user;
    private String theme;

    private SessionMenager(){
        this.theme = "default";
    }

    public static SessionMenager getInstance(){
        if(instance == null){
            instance = new SessionMenager();
        }
        return instance;
    }

    public void setCurrentUser(User user){
        this.user = user;
    }

    public User getCurrentUser(){
        return user;
    }
}
