package com.atelier.serviceorder.repo;

import com.atelier.serviceorder.repo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
