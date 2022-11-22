package com.pollutionapp.user.test.service;


import com.pollutionapp.user.model.User;
import com.pollutionapp.user.repository.UserRepository;
import com.pollutionapp.user.service.UserAuthServiceImpl;
import com.pollutionapp.user.exception.UserAlreadyExistsException;
import com.pollutionapp.user.exception.UserNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Optional;
public class UserAuthServiceImplTest {

    @Mock
    private UserRepository userAuthRepository;

    private User user;
    @InjectMocks
    private UserAuthServiceImpl userAuthServiceImpl;

    Optional<User> optional;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setName("Jhon123");
        user.setPassword("123456");
        user.setEmail("jhon@gmail.com");
        user.setCreatedAt(LocalDateTime.now());
        optional = Optional.of(user);
    }

    @Test
    @DisplayName("Test for saving user success")
    public void testSaveUserSuccess() throws UserAlreadyExistsException {

        Mockito.when(userAuthRepository.save(user)).thenReturn(user);
        boolean flag = userAuthServiceImpl.saveUser(user);
        assertEquals(true, flag);

    }
    @Test
    @DisplayName("Test for saving user  failure")
    public void testSaveUserFailure()  {
        Mockito.when(userAuthRepository.findById("jhon@gmail.com")).thenReturn(optional);
        Mockito.when(userAuthRepository.save(user)).thenReturn(user);
        assertThrows(
        		UserAlreadyExistsException.class,
                    () -> { userAuthServiceImpl.saveUser(user); });
    }
    @Test
    @DisplayName("Test for find user by email")
    public void testFindByEmail() throws UserNotFoundException {
        Mockito.when(userAuthRepository.findById("jhon@gmail.com")).thenReturn(optional);
        User fetchedUser = userAuthServiceImpl.getByEmail("jhon@gmail.com").get();
        assertEquals("jhon@gmail.com", fetchedUser.getEmail());
    }

    @Test
    @DisplayName("Test for updating user password")
    public void testUpdatePassword() throws UserNotFoundException {
        Mockito.when(userAuthRepository.findById("jhon@gmail.com")).thenReturn(optional);
        User fetchedUser = userAuthServiceImpl.getByEmail("jhon@gmail.com").get();
        userAuthServiceImpl.updatePassword(fetchedUser,"123");
        assertEquals("123", fetchedUser.getPassword());
    }


    @Test
    public void testFindByUserIdAndPassword() throws UserNotFoundException {
        Mockito.when(userAuthRepository.findUserByEmailAndPassword("jhon@gmail.com", "123456")).thenReturn(user);
        User fetchedUser = userAuthServiceImpl.findUserByEmailAndPassword("jhon@gmail.com", "123456");
        assertEquals("jhon@gmail.com", fetchedUser.getEmail());
    }
}
