package com.ash.pokemon.repository;

import com.ash.pokemon.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> { }
