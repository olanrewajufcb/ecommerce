package com.ecommerce.myboutique.service;

import com.ecommerce.myboutique.entity.Order;
import com.ecommerce.myboutique.entity.OrderItem;
import com.ecommerce.myboutique.entity.Product;
import com.ecommerce.myboutique.repository.OrderItemRepository;
import com.ecommerce.myboutique.repository.OrderRepository;
import com.ecommerce.myboutique.repository.ProductRepository;
import com.ecommerce.myboutique.web.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderItemService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public List<OrderItemDto> findAll(){
        log.debug("Request to get all OrderItems");
        return this.orderItemRepository.findAll()
                .stream()
                .map(OrderItemService::mapToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly= true)
    public OrderItemDto findById(Long id){
        log.debug("Request to get an order item");
        return this.orderItemRepository.findById(id)
                .map(OrderItemService::mapToDto)
                .orElse(null);

    }

    public OrderItemDto create(OrderItemDto orderItemDto){
        log.debug("Request to create OrderItem : {} ", orderItemDto);
        Order order = this.orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new IllegalStateException("The Order does not exist!"));
        Product product = this.productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new IllegalStateException("The Product does not exist!"));
        return mapToDto(
                this.orderItemRepository.save(
                        new OrderItem(orderItemDto.getQuantity(),
                                product,
                                order)
                )
        );
    }

    public void delete(Long id){
        log.debug("Request to delete OrderItem : {}", id);
        this.orderItemRepository.deleteById(id);
    }

    public static OrderItemDto mapToDto(OrderItem orderItem){
        if(orderItem != null){
            return new OrderItemDto(
                    orderItem.getId(),
                    orderItem.getQuantity(),
                    orderItem.getProduct().getId(),
                    orderItem.getOrder().getId()
            );
        }
        return null;
    }
}
