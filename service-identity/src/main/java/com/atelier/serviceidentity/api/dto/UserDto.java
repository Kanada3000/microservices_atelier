package com.atelier.serviceidentity.api.dto;

public record UserDto(String name, String phone, String email, String password, String userType) {
}
