package com.stanionutraul.service;


import com.stanionutraul.model.User;
import com.stanionutraul.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void insertUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);

    }

    public User updateUser(Integer id, User updatedUser){
        User existingUser = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User with id " + id + " not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }


    public User getUserById(Integer id){
        return  userRepository.findById(id).orElseThrow(() -> new IllegalStateException(id + "User not found"));
    }


}
