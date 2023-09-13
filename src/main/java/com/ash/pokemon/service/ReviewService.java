package com.ash.pokemon.service;

import com.ash.pokemon.dao.ReviewDao;

import java.util.List;

public interface ReviewService {

    ReviewDao createReview(int pokemonId, ReviewDao reviewDao);

    List<ReviewDao> getReviewsByPokemonId(int id);

    ReviewDao getReviewById(int reviewId, int pokemonId);

    ReviewDao updateReview(int reviewId, int pokemonId, ReviewDao reviewDao);

    void deleteReview(int reviewId, int pokemonId);

}
