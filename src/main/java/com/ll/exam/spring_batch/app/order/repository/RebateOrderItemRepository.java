package com.ll.exam.spring_batch.app.order.repository;

import com.ll.exam.spring_batch.app.order.entity.RebateOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RebateOrderItemRepository extends JpaRepository<RebateOrderItem, Long> {

    Optional<RebateOrderItem> findByOrderItemId(Long id);
}
