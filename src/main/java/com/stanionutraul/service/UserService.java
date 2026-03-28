package com.stanionutraul.service;


import com.stanionutraul.dto.UserRequestDTO;
import com.stanionutraul.dto.UserResponseDTO;
import com.stanionutraul.mapper.UserMapper;
import com.stanionutraul.model.User;
import com.stanionutraul.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream().map(user -> UserMapper.toDTO(user)).collect(Collectors.toList());
    }

    public UserResponseDTO insertUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public void deleteUser(Integer id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);

    }

    public UserResponseDTO updateUser(Integer id, UserRequestDTO dto) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        User updateed = userRepository.save(existingUser);
        return UserMapper.toDTO(updateed);
    }


   public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDTO(user);
   }


}
