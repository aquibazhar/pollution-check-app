package com.pollutionapp.user.test.repository;

import com.pollutionapp.user.model.User;
import com.pollutionapp.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    
    @Autowired
    private UserRepository userAuthRepository;
    
    private User user;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setName("Jhon123");
        user.setPassword("123456");
        user.setEmail("jhon@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
    }

    @AfterEach
    public void tearDown() throws Exception {
    	userAuthRepository.deleteAll();
    }


    @Test
    @DisplayName("Test for user register Successfull")
    public void testRegisterUserSuccess() {
    	userAuthRepository.save(user);
        User fetchUser = userAuthRepository.findById(user.getEmail()).get();
        assertThat(user.getEmail(), is(fetchUser.getEmail()));
    }

    @Test
    @DisplayName("Test for user login Successfull")
    public void testLoginUserSuccess() {
    	userAuthRepository.save(user);
        User fetchUser = userAuthRepository.findById(user.getEmail()).get();
        assertThat(user.getEmail(), is(fetchUser.getEmail()));
    }

}
