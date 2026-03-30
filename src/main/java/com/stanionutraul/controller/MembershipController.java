package com.stanionutraul.controller;


import com.stanionutraul.dto.MembershipRequestDTO;
import com.stanionutraul.dto.MembershipResponseDTO;
import com.stanionutraul.model.Membership;
import com.stanionutraul.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public List<MembershipResponseDTO> getAllMemberships() {
      return  membershipService.getAllMemberships();
    }


    @PostMapping
    public MembershipResponseDTO addMembership(@RequestBody @Valid MembershipRequestDTO membershipRequestDTO) {
        return membershipService.insertMembership(membershipRequestDTO);
    }

    @GetMapping("{id}")
    public MembershipResponseDTO getMembershipById(@PathVariable Integer id) {
        return membershipService.getMembershipById(id);
    }

    @PutMapping("{id}")
    public MembershipResponseDTO updateMembership(@PathVariable Integer id, @RequestBody @Valid MembershipRequestDTO membershipRequestDTO) {
        return membershipService.updateMembership(id, membershipRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteMembership(@PathVariable Integer id) {
        membershipService.deleteMembership(id);
    }

}
