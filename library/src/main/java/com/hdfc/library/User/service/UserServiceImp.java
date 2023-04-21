package com.hdfc.library.User.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.hdfc.library.User.dto.UserDTO;
import com.hdfc.library.User.entity.User;
import com.hdfc.library.User.exception.UserNotFoundException;
import com.hdfc.library.User.repository.UserRepository;

/*  addUser(UserDTO userDTO): This method takes a UserDTO object as a parameter, 
    which contains the details of the user to be added, and returns a User object containing 
    the added user's details.

    updateUser(Long userId, UserDTO userDto) throws UserNotFoundException: This method 
    takes the user's ID and the updated UserDTO object as parameters. If the user exists, 
    the method updates the user with the given details and returns the updated User object. 
    If the user does not exist, the method throws a UserNotFoundException.

    deleteUser(UserDTO userDTO) throws UserNotFoundException: This method takes a UserDTO 
    object as a parameter, which contains the details of the user to be deleted. If the user 
    exists, the method deletes the user and returns void. If the user does not exist, the method 
    throws a UserNotFoundException.

    deleteUserById(Long userId) throws UserNotFoundException: This method takes the user's 
    ID as a parameter. If the user exists, the method deletes the user and returns void. If 
    the user does not exist, the method throws a UserNotFoundException.

    getAllUsers(): This method returns a List of User objects containing details of all the 
    users in the system.

    getByUserId(Long userId) throws UserNotFoundException: This method takes the user's ID as 
    a parameter. If the user exists, the method returns a User object containing the user's details. 
    If the user does not exist, the method throws a UserNotFoundException. */

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAccountStatus(userDTO.getAccountStatus());

        return userRepository.save(user);

    }

    @Override
    public User updateUser(Long userId, UserDTO userDto) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setAccountStatus(userDto.getAccountStatus());
            return userRepository.save(user);

        } else {
            throw new UserNotFoundException();
        }

    }

    @Override
    public void deleteUser(UserDTO userDTO) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userDTO.getUserId());
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteUserById(Long userId) throws UserNotFoundException {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User Not Found with id " + userId);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new User(
                        user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getAccountStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public User getByUserId(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new User(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAccountStatus());
        } else {
            throw new UserNotFoundException("User not found with id " + userId);
        }
    }

}