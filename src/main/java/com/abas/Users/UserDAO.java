package com.abas.Users;

import java.util.UUID;

public class UserDAO {
    private static User[] users = new User[2];


    static{
        users[0] = new User(UUID.randomUUID(), "Abas");
        users[1] = new User(UUID.randomUUID(), "james");

    }

    public User[] getUsers(){
        return users;
    }

    public Boolean UserExists(User user){
        for(User u : users){
            return user != null & u.equals(user);
        }
        return false;
    }


}
