package com.abas.Users;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User[] findAll(){
        return userDAO.getUsers();
    }



}
