package com.stanionutraul.service;


import com.stanionutraul.dto.MembershipRequestDTO;
import com.stanionutraul.dto.MembershipResponseDTO;
import com.stanionutraul.mapper.MembershipMapper;
import com.stanionutraul.mapper.UserMapper;
import com.stanionutraul.model.Membership;
import com.stanionutraul.repository.MembershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public List<MembershipResponseDTO> getAllMemberships() {
        return membershipRepository.findAll().stream().map(member -> MembershipMapper.toDTO(member)).collect(Collectors.toList());
    }

    public MembershipResponseDTO insertMembership(MembershipRequestDTO membershipRequestDTO) {
        Membership membership = MembershipMapper.toEntity(membershipRequestDTO);
        Membership savedMembership = membershipRepository.save(membership);
        return MembershipMapper.toDTO(savedMembership);
    }

    public void deleteMembership(Integer id) {
        if(!membershipRepository.existsById(id)) {
            throw new RuntimeException("Membership not found");
        }
        membershipRepository.deleteById(id);
    }

    public MembershipResponseDTO updateMembership(Integer id, MembershipRequestDTO membershipRequestDTO) {
        Membership existingMembership = membershipRepository.findById(id).orElseThrow(() -> new RuntimeException("Membership not found"));

        existingMembership.setType(membershipRequestDTO.getType());
        existingMembership.setPrice(membershipRequestDTO.getPrice());
        existingMembership.setDuration(membershipRequestDTO.getDuration());
        Membership updatedMembership = membershipRepository.save(existingMembership);

        return MembershipMapper.toDTO(updatedMembership);

    }

    public MembershipResponseDTO getMembershipById(Integer id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        return MembershipMapper.toDTO(membership);
    }


}
