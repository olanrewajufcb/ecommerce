package com.ecommerce.myboutique.web;

import com.ecommerce.myboutique.service.OrderItemService;
import com.ecommerce.myboutique.web.dto.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.myboutique.common.Web.API;

@RestController
@RequestMapping(API + "/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;
    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItemDto> findAll(){
        return this.orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItemDto findById(@PathVariable Long id){
        return this.orderItemService.findById(id);
    }
    @PostMapping
    public OrderItemDto create(@RequestBody OrderItemDto orderItemDto){
        return this.orderItemService.create(orderItemDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.orderItemService.delete(id);
    }
}
