package com.pollutionapp.user.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pollutionapp.user.controller.UserAuthController;
import com.pollutionapp.user.model.User;
import com.pollutionapp.user.service.UserAuthService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAuthService userAuthService;


  private User user;

  @InjectMocks
  private UserAuthController userAuthController;

  Optional<User> optional;
  @BeforeEach
  public void setUp() throws Exception {

    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userAuthController).build();

    user = new User();
    user.setName("Jhon123");
    user.setPassword("123456");
    user.setEmail("jhon@gmail.com");
    user.setCreatedAt(LocalDateTime.now());
    optional = Optional.of(user);

  }

  @AfterEach
  public void tearDown(){
    user=null;
    optional=null;
  }

  @Test
  @DisplayName("Test for registering user")
  public void testRegisterUser() throws Exception {
    Mockito.when(userAuthService.saveUser(user)).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/register").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
            .andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());
  }


  @Test
  @DisplayName("Test for login user")
  public void testLoginUser() throws Exception {
    String email = "jhon@gmail.com";
    String password = "123456";
    Mockito.when(userAuthService.saveUser(user)).thenReturn(true);
    Mockito.when(userAuthService.findUserByEmailAndPassword(email, password)).thenReturn(user);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/login").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("Test for get user by email")
  public void testGetUserByEmail() throws Exception {
    String email = "jhon@gmail.com";
    Mockito.when(userAuthService.saveUser(user)).thenReturn(true);
    Mockito.when(userAuthService.getByEmail(email)).thenReturn(optional);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/users/jhon@gmail.com").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("Test for change Password")
  public void testUpdatePassword() throws Exception {
    String email = "jhon@gmail.com";
    User updatedUser=user;
    updatedUser.setPassword("123");
    Mockito.when(userAuthService.saveUser(user)).thenReturn(true);
    Mockito.when(userAuthService.getByEmail(email)).thenReturn(optional);
    Mockito.when(userAuthService.updatePassword(user,"123")).thenReturn(updatedUser);
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v2/users").contentType(MediaType.APPLICATION_JSON).content(jsonToString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
  }


  private static String jsonToString(final Object obj) throws JsonProcessingException {
    String result;
    try {
      ObjectMapper objmapper = new ObjectMapper();
      objmapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      objmapper.registerModule(new JavaTimeModule());
      result = objmapper.writeValueAsString(obj);

    } catch (JsonProcessingException e) {
      result = "Json processing error";
    }
    return result;
  }
}
