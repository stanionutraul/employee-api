package com.stanionutraul.controller;

import com.stanionutraul.dto.TrainerMemberDTO;
import com.stanionutraul.model.User;
import com.stanionutraul.service.TrainerMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainer")
public class TrainerMemberController {

    private final TrainerMemberService trainerMemberService;

    public TrainerMemberController(TrainerMemberService trainerMemberService) {
        this.trainerMemberService = trainerMemberService;
    }

    @GetMapping("/members")
    public List<TrainerMemberDTO> getMembers(Authentication authentication) {
        User trainer = (User) authentication.getPrincipal();

        return trainerMemberService.getMembersForTrainer(trainer);
    }
}