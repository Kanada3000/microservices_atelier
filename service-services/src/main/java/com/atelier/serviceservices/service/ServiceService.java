package com.atelier.serviceservices.service;


import com.atelier.serviceservices.repo.ServiceRepo;
import lombok.RequiredArgsConstructor;
import com.atelier.serviceservices.repo.model.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public final class ServiceService {

    private final ServiceRepo serviceRepo;

    public List<Service> getAll() {
        return serviceRepo.findAll();
    }

    public Service getById(long id) {
        final Optional<Service> service = serviceRepo.findById(id);

        if (service.isPresent())
            return service.get();
        else
            throw new IllegalArgumentException("Invalid service ID");
    }

    public long add(String name, double price) {
        final Service service = new Service(name, price);
        final Service newService = serviceRepo.save(service);
        return newService.getId();
    }

    public void update(long id, String name, double price) {
        Optional<Service> oldService = serviceRepo.findById(id);

        if (oldService.isEmpty())
            throw new IllegalArgumentException("Invalid service ID");

        final Service service = oldService.get();
        if (name != null && !name.isBlank()) service.setName(name);
        if (price != 0) service.setPrice(price);
        serviceRepo.save(service);

    }

    public void delete(long id) {
        serviceRepo.deleteById(id);
    }
}
