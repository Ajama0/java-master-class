package com.abas.Users;

import java.util.List;
import java.util.UUID;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> findAll(){
        return userDAO.getUsers();
    }

    public User findById(UUID id){

        return findAll().stream().filter(user -> user.getId().equals(id)).findFirst().
                orElseThrow(()->new RuntimeException("User with id "+id+" not found"));

    }



}
