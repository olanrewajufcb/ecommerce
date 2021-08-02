package com.ecommerce.myboutique.repository;

import com.ecommerce.myboutique.entity.enumeration.CartStatus;
import com.ecommerce.myboutique.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByStatus(CartStatus status);
    List<Cart> findByStatusAndCustomerId(CartStatus status, Long customerId);
}
