package com.atelier.serviceorder.service;

import com.atelier.serviceorder.api.dto.ServiceDto;
import com.atelier.serviceorder.api.dto.UserDto;
import com.atelier.serviceorder.repo.OrderRepo;
import com.atelier.serviceorder.repo.model.Order;
import com.atelier.serviceorder.repo.model.OrderPoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class OrderService {
    private final String serviceUrlAdress = "http://service-services:8083/services";
    private final String usersUrlAdress = "http://service-identity:8082/users";
    private final OrderRepo orderRepo;

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public Order getById(long id) {
        final Optional<Order> order = orderRepo.findById(id);

        if (order.isPresent())
            return order.get();
        else
            throw new IllegalArgumentException("Invalid order ID");
    }

    public long add(OrderPoints orderPoint, long clientId, long serviceId, long masterId) {
        if (checkUserType(clientId, "CLIENT")) {
            if (checkUserType(masterId, "MASTER")) {
                final Order order = new Order(orderPoint, clientId, serviceId, masterId);
                final Order newOrder = orderRepo.save(order);
                return newOrder.getId();
            } else throw new IllegalArgumentException("Invalid master!");
        }
        throw new IllegalArgumentException("Invalid client!");
    }

    public void update(long id, OrderPoints orderPoint, long clientId, long serviceId, long masterId) {
        Optional<Order> oldOrder = orderRepo.findById(id);

        if (oldOrder.isEmpty())
            throw new IllegalArgumentException("Invalid order ID");

        final Order order = oldOrder.get();
        order.setOrderPoint(orderPoint);
        order.setServiceId(serviceId);
        order.setClientId(clientId);
        order.setTimestamp(new Timestamp(System.currentTimeMillis()));
        order.setMasterId(masterId);
        orderRepo.save(order);
    }

    public UserDto getUserByOrderId(long orderId, String userType) {
        final Optional<Order> order = orderRepo.findById(orderId);

        if (order.isPresent()) {
            if (userType.equals("client") || userType.equals("master")) {
                final long userId;

                if (userType.equals("client")) {
                    userId = order.get().getClientId();
                } else {
                    userId = order.get().getMasterId();
                }

                final RestTemplate restTemplate = new RestTemplate();
                final HttpEntity<Long> userRequest = new HttpEntity<>(userId);

                final ResponseEntity<UserDto> userResponse = restTemplate
                        .exchange(usersUrlAdress + "/" + userId + "/get",
                                HttpMethod.GET, userRequest, UserDto.class);

                if (userResponse.getStatusCode() != HttpStatus.NOT_FOUND)
                    return userResponse.getBody();
                else
                    throw new IllegalArgumentException("User not found!");
            } else {
                throw new IllegalArgumentException("User type not found!");
            }
        } else
            throw new IllegalArgumentException("Invalid order ID");
    }

    public ServiceDto getServiceByOrderId(long orderId) {
        final Optional<Order> order = orderRepo.findById(orderId);

        if (order.isPresent()) {
            final long serviceId = order.get().getServiceId();
            final RestTemplate restTemplate = new RestTemplate();
            final HttpEntity<Long> serviceRequest = new HttpEntity<>(serviceId);

            final ResponseEntity<ServiceDto> serviceResponse = restTemplate
                    .exchange(serviceUrlAdress + "/" + serviceId,
                            HttpMethod.GET, serviceRequest, ServiceDto.class);

            if (serviceResponse.getStatusCode() != HttpStatus.NOT_FOUND)
                return serviceResponse.getBody();
            else
                throw new IllegalArgumentException("Service not found!");
        } else
            throw new IllegalArgumentException("Invalid order ID");
    }

    public boolean checkUserType(long id, String userType) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(id);

        final ResponseEntity<UserDto> userResponse = restTemplate
                .exchange(usersUrlAdress + "/" + id + "/get",
                        HttpMethod.GET, userRequest, UserDto.class);

        if (userResponse.getStatusCode() != HttpStatus.NOT_FOUND){
            System.out.println(Objects.requireNonNull(userResponse.getBody()).userType());
            return Objects.requireNonNull(userResponse.getBody()).userType().equals(userType);
        }

        else
            throw new IllegalArgumentException("User not found!");
    }

    public void delete(long id) {
        orderRepo.deleteById(id);
    }
}
