package com.hdfc.library.User.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hdfc.library.User.dto.UserDTO;
import com.hdfc.library.User.entity.User;
import com.hdfc.library.User.exception.UserNotFoundException;
import com.hdfc.library.User.service.UserServiceImp;

@RestController
@RequestMapping("/api/v2/user")
public class UserRestController {

    @Autowired
    private UserServiceImp userService;

    public UserRestController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getbyuserId/{userId}")
    public User getByUserId(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getByUserId(userId);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody @Valid UserDTO userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDTO userDto) throws UserNotFoundException {
        return userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody @Valid UserDTO userDTO) throws UserNotFoundException {
        userService.deleteUser(userDTO);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUserById(@PathVariable Long userId) throws UserNotFoundException {
        userService.deleteUserById(userId);
    }

}
