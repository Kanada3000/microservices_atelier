package com.atelier.serviceclaim.api.dto;

public record UserDto(String email, String name, String password, String phone, String userType) {
}