package com.ecommerce.myboutique.service;

import com.ecommerce.myboutique.entity.Address;
import com.ecommerce.myboutique.entity.Cart;
import com.ecommerce.myboutique.entity.Order;
import com.ecommerce.myboutique.entity.enumeration.OrderStatus;
import com.ecommerce.myboutique.repository.OrderRepository;
import com.ecommerce.myboutique.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    public List<OrderDto> findAll(){
        log.debug("Request to get all Orders");
        return this.orderRepository.findAll()
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true )
    public OrderDto findById(Long id){
        return this.orderRepository.findById(id).map(OrderService::mapToDto)
                .orElse(null);
    }

    public List<OrderDto> findAllByUser(Long id){
        return this.orderRepository.findByCartCustomer_Id(id)
                .stream().map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }


    public OrderDto create(OrderDto orderDto) {
        log.debug("Request to create Order : {}", orderDto);
        return mapToDto(
                this.orderRepository.save(
                        new Order(
                                BigDecimal.ZERO,
                                OrderStatus.CREATION,
                                null,
                                null,
                                AddressService.createFromDto(orderDto.getShipmentAddress()),
                                Collections.emptySet(),
                                null
                        )
                )
        );
    }

    public Order create(Cart cart, Address address) {
        log.debug("Request to create Order with a Cart : {}", cart);
        return this.orderRepository.save(
                new Order(
                        BigDecimal.ZERO,
                        OrderStatus.CREATION,
                        null,
                        null,
                        address,
                        Collections.emptySet(),
                        cart
                )
        );
    }
     public static OrderDto mapToDto(Order order){
        if(order != null){
            return new OrderDto(
                    order.getId(),
                    order.getTotalPrice(),
                    order.getStatus().name(),
                    order.getShipped(),
                    PaymentService.mapToDto(order.getPayment()),
                    AddressService.mapToDto(order.getShipmentAddress()),
                    order.getOrderItems()
                    .stream()
                    .map(OrderItemService::mapToDto)
                    .collect(Collectors.toSet())
            );
        }
        return null;
     }

     public void delete(Long id){
         log.debug("Request to delete Order : {}", id);
         this.orderRepository.deleteById(id);
     }
}
