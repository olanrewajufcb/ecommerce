package com.ecommerce.myboutique.web;

import com.ecommerce.myboutique.service.CustomerService;
import com.ecommerce.myboutique.web.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.myboutique.common.Web.API;

@RestController
@RequestMapping(API + "/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> findAll(){
        return this.customerService.findAll();
    }
    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable Long id){
        return this.customerService.findById(id);
    }
    @GetMapping("/active")
    public List<CustomerDto> findAllActive(){
        return this.customerService.findAllActive();
    }

    @GetMapping("/inactive")
    public List<CustomerDto> findAllInactive(){
        return this.customerService.findAllInactive();
    }

    @PostMapping
    public CustomerDto create(CustomerDto customerDto){
        return this.customerService.create(customerDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.customerService.delete(id);
    }
}
