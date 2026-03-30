package com.stanionutraul.mapper;

import com.stanionutraul.dto.MembershipRequestDTO;
import com.stanionutraul.dto.MembershipResponseDTO;
import com.stanionutraul.model.Membership;

public class MembershipMapper {

    public static MembershipResponseDTO toDTO(Membership m) {
        MembershipResponseDTO dto = new MembershipResponseDTO();
        dto.setId(m.getId());
        dto.setType(m.getType());
        dto.setPrice(m.getPrice());
        dto.setDuration(m.getDuration());
        return dto;
    }

    public static Membership toEntity(MembershipRequestDTO dto) {
        Membership m = new Membership();
        m.setType(dto.getType());
        m.setPrice(dto.getPrice());
        m.setDuration(dto.getDuration());
        return m;
    }
}
