package com.CoworkingSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UsersBaseTest {
    private User user;
    private User user2;
    private User user3;
    private UsersBase test_base;

    @BeforeEach
    void setUp(){
        user = new User("Andrey");
        user2 = new User("Roman");
        user3 = new User("Roman");
        test_base= new UsersBase();
    }
    @Test
    void getOrCreateUser_ShouldAddAllUSersToHashMap(){
        test_base.getOrCreateUser(user.getUserName());
        boolean userExists = test_base.userExist(user.getUserName());
        assertTrue(userExists);
    }
    @Test
    void getOrCreateUser_ShouldAddTwoUsers(){
        test_base.getOrCreateUser(user2.getUserName());
        test_base.getOrCreateUser(user3.getUserName());

        boolean user2Exists = test_base.userExist(user2.getUserName());
        boolean user3Exists = test_base.userExist(user3.getUserName());
        assertTrue(user2Exists&&user3Exists);
    }

}
