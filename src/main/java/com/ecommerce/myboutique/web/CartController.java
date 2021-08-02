package com.ecommerce.myboutique.web;

import com.ecommerce.myboutique.service.CartService;
import com.ecommerce.myboutique.web.dto.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.myboutique.common.Web.API;

@RestController
@RequestMapping(API + "/carts")
public class CartController {


    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartDto> findAll(){
        return this.cartService.findAll();
    }

    @GetMapping("/active")
    public List<CartDto> findAllActiveCarts(){
        return this.cartService.findAllActiveCarts();
    }

    @GetMapping("/customer/{id}")
    public CartDto getActiveCartForCustomer(@PathVariable("id") Long customerId){
        return this.cartService.getActiveCart(customerId);
    }
    @GetMapping("/{id}")
    public CartDto findById(@PathVariable Long id){
        return this.cartService.findById(id);
    }

    @PostMapping("/customer/{id}")
    public CartDto create(@PathVariable("id") Long customerId){
        return this.cartService.createDto(customerId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.cartService.delete(id);
    }


}
