package com.atelier.serviceorder.api.dto;

public record UserDto(String email, String name, String password, String phone, String userType) {
}
