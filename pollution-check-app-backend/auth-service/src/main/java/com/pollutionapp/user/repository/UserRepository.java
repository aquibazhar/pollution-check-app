package com.pollutionapp.user.repository;

import com.pollutionapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UserRepository extends JpaRepository<User,String>{

	public User findUserByEmailAndPassword(String userId, String password);

}
