package com.ecommerce.myboutique.service;

import com.ecommerce.myboutique.entity.Cart;
import com.ecommerce.myboutique.entity.Customer;
import com.ecommerce.myboutique.entity.enumeration.CartStatus;
import com.ecommerce.myboutique.repository.CartRepository;
import com.ecommerce.myboutique.repository.CustomerRepository;
import com.ecommerce.myboutique.web.dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final OrderService orderService;

    public List<CartDto> findAll(){
       return cartRepository.findAll()
               .stream()
               .map(CartService::mapToDto)
               .collect(Collectors.toList());
    }

    public List<CartDto> findAllActiveCarts(){
        return cartRepository.findByStatus(CartStatus.NEW)
                .stream()
                .map(CartService::mapToDto)
                .collect(Collectors.toList());
    }
    
    public Cart create(Long customerId){
        if(this.getActiveCart(customerId) == null){
            Customer customer = this.customerRepository.findById(customerId)
                    .orElseThrow( () -> new IllegalStateException("The Customer does not exist"));
            Cart cart = new Cart(customer, CartStatus.NEW);
            return this.cartRepository.save(cart);
        }else {
            throw new IllegalStateException("There's already an active cart!");
        }
    }

    public CartDto getActiveCart(Long customerId) {
        List<Cart> carts = this.cartRepository
                .findByStatusAndCustomerId(CartStatus.NEW, customerId);
        if (carts != null){
            if (carts.size() == 1){
                return mapToDto(carts.get(0));
            }
            if (carts.size() > 1){
                throw  new IllegalStateException("Many active carts detected!");
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public CartDto findById(Long id){
        log.debug("Request to get Cart : {}", id);
        return this.cartRepository.findById(id).map(CartService::mapToDto)
                .orElse(null);
    }

    public void delete(Long id){
        log.debug("Request to delete Cart : {}" , id);
        Cart cart = this.cartRepository.findById(id).orElseThrow( () ->
                new IllegalStateException("Cannot find cart with id : {}" + id));
        cart.setStatus(CartStatus.CANCELED);
        this.cartRepository.save(cart);
    }

    public CartDto createDto(Long customerId){
        return mapToDto(this.create(customerId));
    }

    private static CartDto mapToDto(Cart cart) {
        if(cart != null){
            return new CartDto(
                    cart.getId(),
                    cart.getOrder().getId(),
                    CustomerService.mapToDto(cart.getCustomer()),
                    cart.getStatus().name()
            );
        }
        return null;
    }
}
