package com.ash.pokemon.controllers;

import com.ash.pokemon.dao.ReviewDao;
import com.ash.pokemon.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDao> createReview(
            @PathVariable (value = "pokemonId") int pokemonId,
            @RequestBody ReviewDao reviewDao) {
        System.out.println(reviewDao);
        return ResponseEntity.ok(reviewService.createReview(pokemonId, reviewDao));
    }

    @GetMapping("pokemon/{pokemonId}/reviews")
    public ResponseEntity<List<ReviewDao>> getReviewsByPokemonId(
            @PathVariable(value = "pokemonId") int pokemonId) {
        return ResponseEntity.ok(reviewService.getReviewsByPokemonId(pokemonId));
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDao> getReviewById(
            @PathVariable(value = "pokemonId") int pokemonId,
            @PathVariable(value = "id") int reviewId) {
        ReviewDao reviewDao = reviewService.getReviewById(reviewId, pokemonId);
        return ResponseEntity.ok(reviewDao);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDao> updateReview(
            @PathVariable(value = "pokemonId") int pokemonId,
            @PathVariable(value = "id") int reviewId,
            @RequestBody ReviewDao reviewDao) {
        ReviewDao newReviewDao = reviewService.updateReview(reviewId, pokemonId, reviewDao);
        return ResponseEntity.ok(newReviewDao);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(
            @PathVariable(value = "pokemonId") int pokemonId,
            @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(reviewId, pokemonId);
        return ResponseEntity.ok("Review deleted successfully");
    }

}
