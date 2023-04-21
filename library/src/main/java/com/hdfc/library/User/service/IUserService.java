package com.hdfc.library.User.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hdfc.library.User.dto.UserDTO;
import com.hdfc.library.User.entity.User;
import com.hdfc.library.User.exception.UserNotFoundException;

@Service
public interface IUserService {

    public User addUser(UserDTO userDTO);

    public User updateUser(Long userId, UserDTO userDto) throws UserNotFoundException;

    public void deleteUser(UserDTO userDTO) throws UserNotFoundException;

    public void deleteUserById(Long userId) throws UserNotFoundException;

    public List<User> getAllUsers();

    public User getByUserId(Long userId) throws UserNotFoundException;

}
