package com.ll.exam.spring_batch.app.cart.repository;

import com.ll.exam.spring_batch.app.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByMemberIdAndProductOptionId(Long memberId, Long productOptionId);

    List<CartItem> findAllByMemberId(Long id);
}
