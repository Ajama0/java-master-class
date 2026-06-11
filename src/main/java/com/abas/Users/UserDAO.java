package com.abas.Users;


import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    boolean userExists(User user);



        }




