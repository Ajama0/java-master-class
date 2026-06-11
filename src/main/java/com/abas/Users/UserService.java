package com.abas.Users;

import java.util.UUID;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User[] findAll(){
        return userDAO.getUsers();
    }

    public User findById(UUID id){
        User[] users = findAll();
        for(User user : users){
            if(user!= null && user.getId().equals(id)){
                return user;
            }
        }
        throw new IllegalArgumentException("User not found");
    }



}
