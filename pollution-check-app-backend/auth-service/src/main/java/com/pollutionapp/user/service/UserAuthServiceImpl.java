package com.pollutionapp.user.service;

import com.pollutionapp.user.model.User;
import com.pollutionapp.user.exception.UserAlreadyExistsException;
import com.pollutionapp.user.exception.UserNotFoundException;
import com.pollutionapp.user.repository.UserRepository;

import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	private UserRepository userAuthRepo;

	public UserAuthServiceImpl(UserRepository userAuthRepo) {
		super();
		this.userAuthRepo = userAuthRepo;
	}


	@Override
    public User findUserByEmailAndPassword(String userEmail, String password) throws UserNotFoundException {
		User user = userAuthRepo.findUserByEmailAndPassword(userEmail, password);
		if(user != null)
			return user;
		return null;
    }

	@Override
	public Optional<User> getByEmail(String email) {
		return userAuthRepo.findById(email);
	}

	@Override
	public User updatePassword(User user,String password) {
		user.setPassword(password);
		return  userAuthRepo.save(user);
	}

    @Override
    public boolean saveUser(User user) throws UserAlreadyExistsException {
    	Optional<User> isUserExists = userAuthRepo.findById(user.getEmail());
    	if(isUserExists.isEmpty()) {
    		userAuthRepo.save(user);
    		return true;
    	}
    	else{
    		throw new UserAlreadyExistsException("User Already Exists.");
    	}
    }
}
