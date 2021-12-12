package com.atelier.serviceorder.api;


import com.atelier.serviceorder.api.dto.OrderDto;
import com.atelier.serviceorder.api.dto.ServiceDto;
import com.atelier.serviceorder.api.dto.UserDto;
import com.atelier.serviceorder.repo.model.Order;
import com.atelier.serviceorder.repo.model.OrderPoints;
import com.atelier.serviceorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public final class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> showAll() {
        final List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> showById(@PathVariable long id) {
        try {
            final Order order = orderService.getById(id);

            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/service")
    public ResponseEntity<ServiceDto> getServiceByOrderId(@PathVariable long id) {
        try {
            final ServiceDto serviceDto = orderService.getServiceByOrderId(id);

            return ResponseEntity.ok(serviceDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/user/{userType}")
    public ResponseEntity<UserDto> getClientByOrderId(@PathVariable(name = "id") long id,
                                                      @PathVariable(name = "userType") String userType) {
        try {
            final UserDto userDto = orderService.getUserByOrderId(id, userType);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDto orderDto) {
        OrderPoints orderPoints = OrderPoints.valueOf(orderDto.orderPoint());
        long clientId = orderDto.clientId();
        long masterId = orderDto.masterId();
        long serviceId = orderDto.serviceId();

        try {
            final long orderId = orderService.add(orderPoints, clientId, serviceId, masterId);
            final String orderUri = String.format("/orders/%d", orderId);

            return ResponseEntity.created(URI.create(orderUri)).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody OrderDto orderDto) {
        OrderPoints orderPoints = OrderPoints.valueOf(orderDto.orderPoint());
        long clientId = orderDto.clientId();
        long masterId = orderDto.masterId();
        long serviceId = orderDto.serviceId();

        try {
            orderService.update(id, orderPoints, clientId, serviceId, masterId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
