package com.hdfc.library.UserTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdfc.library.User.dto.UserDTO;
import com.hdfc.library.User.entity.User;
import com.hdfc.library.User.exception.UserNotFoundException;
import com.hdfc.library.User.service.IUserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    private UserDTO userDto;

    @BeforeEach
    public void setup() {
        userDto = new UserDTO();
        userDto.setUserId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@test.com");
        userDto.setPassword("password");
        userDto.setAccountStatus("Active");
    }

    @Test
    public void testAddUser() {
        User user = userService.addUser(userDto);
        assertEquals(userDto.getUserId(), user.getUserId());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getAccountStatus(), user.getAccountStatus());
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException {
        User user = userService.addUser(userDto);
        userDto.setFirstName("Jane");
        userDto.setLastName("Doe");
        userDto.setEmail("jane.doe@test.com");
        userDto.setPassword("newpassword");
        userDto.setAccountStatus("INACTIVE");
        User updatedUser = userService.updateUser(user.getUserId(), userDto);
        assertEquals(userDto.getFirstName(), updatedUser.getFirstName());
        assertEquals(userDto.getLastName(), updatedUser.getLastName());
        assertEquals(userDto.getEmail(), updatedUser.getEmail());
        assertEquals(userDto.getPassword(), updatedUser.getPassword());
        assertEquals(userDto.getAccountStatus(), updatedUser.getAccountStatus());
    }

    @Test
    public void testGetAllUsers() {
        userService.addUser(userDto);
        List<User> users = userService.getAllUsers();
        assertEquals(12, users.size());
    }

    @Test
    public void testGetByUserId() throws UserNotFoundException {
        User user = userService.addUser(userDto);
        User retrievedUser = userService.getByUserId(user.getUserId());
        assertEquals(user.getUserId(), retrievedUser.getUserId());
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getLastName(), retrievedUser.getLastName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
        assertEquals(user.getAccountStatus(), retrievedUser.getAccountStatus());
    }

}
