package com.stanionutraul.dto;

public class UserResponseDTO {

    private Integer id;
    private String name;
    private String email;
    private String role;

    private MembershipResponseDTO membership;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MembershipResponseDTO getMembership() {
        return membership;
    }

    public void setMembership(MembershipResponseDTO membership) {
        this.membership = membership;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}