package com.ecommerce.myboutique.web;

import com.ecommerce.myboutique.service.CategoryService;
import com.ecommerce.myboutique.web.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.myboutique.common.Web.API;

@RestController
@RequestMapping(API + "/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> findAll(){
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id){
        return this.categoryService.findById(id);
    }
    @PostMapping
    public CategoryDto create(CategoryDto categoryDto){
        return this.categoryService.create(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.categoryService.delete(id);
    }
}
