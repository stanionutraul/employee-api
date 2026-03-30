package com.stanionutraul.service;


import com.stanionutraul.dto.UserRequestDTO;
import com.stanionutraul.dto.UserResponseDTO;
import com.stanionutraul.mapper.UserMapper;
import com.stanionutraul.model.Membership;
import com.stanionutraul.model.User;
import com.stanionutraul.repository.MembershipRepository;
import com.stanionutraul.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public final UserRepository userRepository;
    public final MembershipRepository membershipRepository;


    public UserService(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream().map(user -> UserMapper.toDTO(user)).collect(Collectors.toList());
    }

    public UserResponseDTO insertUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        if(dto.getMembershipId() != null) {
            Membership membership = membershipRepository.findById(dto.getMembershipId()).orElseThrow(() -> new RuntimeException("Membership not found"));

            user.setMembership(membership);
        }
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
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());

        if (dto.getMembershipId() != null) {
            Membership membership = membershipRepository.findById(dto.getMembershipId())
                    .orElseThrow(() -> new RuntimeException("Membership not found"));

            existingUser.setMembership(membership);
        }

        User updated = userRepository.save(existingUser);
        return UserMapper.toDTO(updated);
    }


   public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toDTO(user);
   }


}
