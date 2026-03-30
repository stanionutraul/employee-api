package com.stanionutraul.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MembershipRequestDTO {
    @NotBlank(message = "Type is required")
    private String type;
    @NotNull(message = "Price is required")
    private Double price;
    @NotNull(message = "Duration is required")
    private Integer duration;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
