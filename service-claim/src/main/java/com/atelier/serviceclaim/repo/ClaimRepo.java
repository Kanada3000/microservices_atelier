package com.atelier.serviceclaim.repo;

import com.atelier.serviceclaim.repo.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepo extends JpaRepository<Claim, Long> {
}
