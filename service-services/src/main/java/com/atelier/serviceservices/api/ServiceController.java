package com.atelier.serviceservices.api;

import com.atelier.serviceservices.api.dto.ServiceDto;
import com.atelier.serviceservices.repo.model.Service;
import com.atelier.serviceservices.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/services")
@RestController
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<Service>> showAll() {
        final List<Service> services = serviceService.getAll();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> showById(@PathVariable long id) {
        try {
            final Service service = serviceService.getById(id);

            return ResponseEntity.ok(service);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ServiceDto serviceDto) {
        String name = serviceDto.name();
        double price = serviceDto.price();

        final long serviceId = serviceService.add(name, price);
        final String serviceUri = String.format("/services/%d", serviceId);

        return ResponseEntity.created(URI.create(serviceUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody ServiceDto serviceDto) {
        String name = serviceDto.name();
        double price = serviceDto.price();

        try {
            serviceService.update(id, name, price);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
