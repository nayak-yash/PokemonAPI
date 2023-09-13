package com.ash.pokemon.service;

import com.ash.pokemon.dao.PokemonDao;
import com.ash.pokemon.dao.PokemonResponse;


public interface PokemonService {

    PokemonDao createPokemonDao(PokemonDao pokemonDao);

    PokemonResponse getAllPokemon(int pageNo, int pageSize);

    PokemonDao getPokemonById(int id);

    PokemonDao updatePokemon(PokemonDao pokemonDao, int id);

    void deletePokemon(int id);

}
