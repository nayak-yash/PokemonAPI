package com.ash.pokemon.service.impl;

import com.ash.pokemon.dao.ReviewDao;
import com.ash.pokemon.exceptions.PokemonNotFoundException;
import com.ash.pokemon.exceptions.ReviewNotFoundException;
import com.ash.pokemon.models.Pokemon;
import com.ash.pokemon.models.Review;
import com.ash.pokemon.repository.PokemonRepository;
import com.ash.pokemon.repository.ReviewRepository;
import com.ash.pokemon.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepo;
    private PokemonRepository pokemonRepo;

    public ReviewServiceImpl(ReviewRepository reviewRepo, PokemonRepository pokemonRepo) {
        this.reviewRepo = reviewRepo;
        this.pokemonRepo = pokemonRepo;
    }


    @Override
    public void deleteReview(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon with associated review not found"));
        Review review = reviewRepo.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review with associate pokemon not found"));
        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a Pokemon");
        }
        reviewRepo.delete(review);
    }

    @Override
    public ReviewDao createReview(int pokemonId, ReviewDao reviewDao) {
        Review review = mapToEntity(reviewDao);
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon with associated review not found"));
        review.setPokemon(pokemon);
        Review newReview = reviewRepo.save(review);
        return mapToDao(newReview);
    }

    @Override
    public ReviewDao updateReview(int reviewId, int pokemonId, ReviewDao reviewDao) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon with associated review not found"));
        Review review = reviewRepo.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review with associate pokemon not found"));
        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a Pokemon");
        }
        review.setTitle(reviewDao.getTitle());
        review.setContent(reviewDao.getContent());
        review.setStars(reviewDao.getStars());
        Review updateReview = reviewRepo.save(review);
        return mapToDao(updateReview);
    }

    @Override
    public List<ReviewDao> getReviewsByPokemonId(int id) {
        List<Review> reviews = reviewRepo.findByPokemonId(id);
        return reviews.stream().map(this::mapToDao).toList();
    }

    @Override
    public ReviewDao getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon with associated review not found"));
        Review review = reviewRepo.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review with associate pokemon not found"));
        if(review.getPokemon().getId() != pokemon.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a Pokemon");
        }
        return mapToDao(review);
    }

    private ReviewDao mapToDao(Review review) {
        ReviewDao reviewDao = new ReviewDao();
        reviewDao.setId(review.getId());
        reviewDao.setTitle(review.getTitle());
        reviewDao.setContent(review.getContent());
        reviewDao.setStars(review.getStars());
        return reviewDao;
    }

    private Review mapToEntity(ReviewDao reviewDao) {
        Review review = new Review();
        review.setId(reviewDao.getId());
        review.setTitle(reviewDao.getTitle());
        review.setContent(reviewDao.getContent());
        review.setStars(reviewDao.getStars());
        return review;
    }
}
