package com.abas.Users;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserFakerDataAccessService implements UserDAO {

    List<User> fakerUsers = new ArrayList<>();

    public UserFakerDataAccessService() {
        Faker faker = new Faker();

        for(int i = 0; i<20; i++){
            fakerUsers.add(new User(UUID.randomUUID(), faker.name().fullName()));
        }

        /// this allows us to see the first user so we can use it in the console menu
        //System.out.println(fakerUsers);

    }

    @Override
    public boolean userExists(User user) {
        return fakerUsers.contains(user);

    }

    @Override
    public List<User> getUsers() {
        return fakerUsers;
    }
}
