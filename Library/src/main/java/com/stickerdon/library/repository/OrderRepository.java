package com.stickerdon.library.repository;

import com.stickerdon.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
