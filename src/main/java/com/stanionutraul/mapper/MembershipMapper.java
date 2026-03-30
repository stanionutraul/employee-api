package com.stanionutraul.mapper;

import com.stanionutraul.dto.MembershipRequestDTO;
import com.stanionutraul.dto.MembershipResponseDTO;
import com.stanionutraul.model.Membership;

public class MembershipMapper {

    public static MembershipResponseDTO toDTO(Membership membership) {
        MembershipResponseDTO dto = new MembershipResponseDTO();
        dto.setType(membership.getType());
        dto.setPrice(membership.getPrice());
        dto.setDuration(membership.getDuration());
        return dto;
    }

    public static Membership toEntity(MembershipRequestDTO dto) {
        Membership membership = new Membership();
        membership.setType(dto.getType());
        membership.setPrice(dto.getPrice());
        membership.setDuration(dto.getDuration());
        return membership;
    }
}
