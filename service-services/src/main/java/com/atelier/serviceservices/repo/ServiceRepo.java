package com.atelier.serviceservices.repo;

import com.atelier.serviceservices.repo.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Long> {
}
