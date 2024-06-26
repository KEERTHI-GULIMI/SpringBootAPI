package com.siemens.SpringBootAPI.service;


import com.siemens.SpringBootAPI.entity.User;
import com.siemens.SpringBootAPI.models.*;
import com.siemens.SpringBootAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails saveUser(UserRequest userRequest) throws myCustomException  {

        User user = new User();

        user.setUserName(userRequest.getUserName());
        user.setUserEmail(userRequest.getUserEmail());
        user.setContactNo(userRequest.getContactNo());
        User savedUser = userRepository.save(user);
        return convertUserToUserDetails(savedUser);

    }

    public List<UserDetails> getAllUsers() {

        List<User> allUsers = userRepository.findAll();
        List<UserDetails> userDetailsList = new ArrayList<>();

        allUsers.forEach(item -> userDetailsList.add(convertUserToUserDetails(item)));
        return userDetailsList;
    }


    private UserDetails convertUserToUserDetails(User user) {

        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(user.getUserId());
        userDetails.setUserName(user.getUserName());
        userDetails.setUserEmail(user.getUserEmail());
        userDetails.setContactNo(user.getContactNo());
        return userDetails;
    }

    public UserDetails updateUser(Integer id, UserRequest userRequest) throws myCustomException  {

        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setContactNo(userRequest.getContactNo());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserName(userRequest.getUserName());
            User updateUser = userRepository.save(user);
            return convertUserToUserDetails(updateUser);
        } else {
            throw new myCustomException (" user is not present ");
        }
    }


    public UserDetails getUserById(Integer id, User user) throws myException {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return convertUserToUserDetails(optionalUser.get());
        } else {
            throw new myException("invalid user id " + id);
        }
    }


}
