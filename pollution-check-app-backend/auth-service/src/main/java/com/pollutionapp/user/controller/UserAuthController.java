package com.pollutionapp.user.controller;

import com.pollutionapp.user.dto.UserDto;
import com.pollutionapp.user.exception.UserAlreadyExistsException;
import com.pollutionapp.user.exception.UserNotFoundException;
import com.pollutionapp.user.model.User;
import com.pollutionapp.user.service.UserAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
@CrossOrigin(origins = "http://localhost:4200")
public class UserAuthController {
  static final long EXPIRATIONTIME = 300000;
  private Map<String, String> map = new HashMap<>();

  @Autowired
  private UserAuthService userAuthService;

  public UserAuthController(UserAuthService userAuthService) {
    this.userAuthService = userAuthService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody UserDto userDto)
    throws UserNotFoundException {
    User user = new User(userDto);
    user.setCreatedAt(LocalDateTime.now());
    try {
      User userById = userAuthService.findUserByEmailAndPassword(
        user.getEmail(),
        user.getPassword()
      );
      if (userById == null) {
        userAuthService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
      }
    } catch (UserAlreadyExistsException e) {
      return new ResponseEntity<User>(HttpStatus.CONFLICT);
    }
    return new ResponseEntity<User>(HttpStatus.CONFLICT);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UserDto userDto) {
    User user = new User(userDto);
    String jwtToken = "";
    try {
      jwtToken = getToken(user.getEmail(), user.getPassword());
      map.clear();
      map.put("message", "user successfully logged in");
      map.put("token", jwtToken);
    } catch (Exception e) {
      String exceptionMsg = e.getMessage();
      map.clear();
      map.put("token", null);
      map.put("message", exceptionMsg);

      return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @CrossOrigin(
    origins = "http://localhost:4200",
    exposedHeaders = "Access-Control-Allow-Origin"
  )
  @PostMapping(value = "/authenticate")
  public ResponseEntity<Map<String, String>> authenticateUser(
    @RequestHeader("Authorization") String token
  ) {
    Map<String, String> response = new HashMap<>();
    if (!validateToken(token.substring(7))) {
      response.put("message", "Invalid token");
      response.put("status", HttpStatus.UNAUTHORIZED.toString());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    response.put("message", "Valid token");
    response.put("status", HttpStatus.OK.toString());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("users/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
    Optional<User> user = userAuthService.getByEmail(email);
    if (user.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @PutMapping("/users")
  public ResponseEntity<User> changePassword(@RequestBody User user) {
    User updateUser = null;
    Optional<User> existingUserOpt = userAuthService.getByEmail(
      user.getEmail()
    );
    if (existingUserOpt.isPresent()) {
      updateUser =
        userAuthService.updatePassword(
          existingUserOpt.get(),
          user.getPassword()
        );
    }
    if (updateUser == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(updateUser);
  }

  public String getToken(String userEmail, String password) throws Exception {
    if (userEmail == null || password == null) {
      throw new ServletException("Please fill in username and password.");
    }

    User isUserExists = userAuthService.findUserByEmailAndPassword(
      userEmail,
      password
    );

    if (isUserExists == null) {
      throw new ServletException("Invalid Credentials.");
    }

    String jwtToken = Jwts
      .builder()
      .setSubject(userEmail)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
      .signWith(SignatureAlgorithm.HS256, "secretkey")
      .compact();

    return jwtToken;
  }

  public boolean validateToken(String token) {
    Claims claims = Jwts
      .parser()
      .setSigningKey("secretkey")
      .parseClaimsJws(token)
      .getBody();
    if (claims != null) {
      return true;
    }
    return false;
  }
}
