package com.pollutionapp.user.service;

import com.pollutionapp.user.model.User;
import com.pollutionapp.user.exception.UserAlreadyExistsException;
import com.pollutionapp.user.exception.UserNotFoundException;

import java.util.Optional;

public interface UserAuthService {

    public Optional<User> getByEmail(String email);
    public User updatePassword(User user,String password);

    public User findUserByEmailAndPassword(String userEmail, String password) throws UserNotFoundException;

    boolean saveUser(User user) throws UserAlreadyExistsException;
}
