package com.atelier.serviceorder.api.dto;

public record OrderDto(String orderPoint, long clientId, long serviceId, long masterId) {
}
