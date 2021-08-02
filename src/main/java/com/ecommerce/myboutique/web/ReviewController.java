package com.ecommerce.myboutique.web;

import com.ecommerce.myboutique.service.ReviewService;
import com.ecommerce.myboutique.web.dto.ReviewDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecommerce.myboutique.common.Web.API;

@RestController
@RequestMapping(API + "/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto> findAll() {
        return this.reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ReviewDto findById(@PathVariable Long id) {
        return this.reviewService.findById(id);
    }

    @PostMapping
    public ReviewDto create(@RequestBody ReviewDto reviewDto) {
        return this.reviewService.createDto(reviewDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.reviewService.delete(id);
    }
}
