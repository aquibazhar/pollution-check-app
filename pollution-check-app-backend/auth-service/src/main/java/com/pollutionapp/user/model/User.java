package com.pollutionapp.user.model;

import com.pollutionapp.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User{
	@Id
	@Column(unique = true)
	private String email;
	private String name;
	private String password;
	private LocalDateTime createdAt;

	public User(UserDto userDto) {
		this.email = userDto.getEmail();
		this.name = userDto.getName();
		this.password = userDto.getPassword();
		this.createdAt = userDto.getCreatedAt();
	}
}
