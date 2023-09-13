package com.ash.pokemon.service.impl;

import com.ash.pokemon.dao.PokemonDao;
import com.ash.pokemon.dao.PokemonResponse;
import com.ash.pokemon.exceptions.PokemonNotFoundException;
import com.ash.pokemon.models.Pokemon;
import com.ash.pokemon.repository.PokemonRepository;
import com.ash.pokemon.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepo;

    public PokemonServiceImpl(PokemonRepository pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }

    @Override
    public PokemonDao createPokemonDao(PokemonDao pokemonDao) {
        Pokemon pokemon = mapToEntity(pokemonDao);
        Pokemon newPokemon = pokemonRepo.save(pokemon);
        return mapToDao(newPokemon);
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons = pokemonRepo.findAll(pageable);
        List<Pokemon> pokemonList = pokemons.getContent();
        List<PokemonDao> content = pokemonList.stream().map(this::mapToDao).toList();
        PokemonResponse response = new PokemonResponse();
        response.setContent(content);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(pokemons.getTotalElements());
        response.setTotalPages(pokemons.getTotalPages());
        response.setLast(pokemons.isLast());
        return response;
    }

    @Override
    public PokemonDao getPokemonById(int id) {
        Pokemon pokemon = pokemonRepo.findById(id).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDao(pokemon);
    }

    @Override
    public PokemonDao updatePokemon(PokemonDao pokemonDao, int id) {
        Pokemon pokemon = pokemonRepo.findById(id).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon could not be updated"));
        pokemon.setName(pokemonDao.getName());
        pokemon.setType(pokemonDao.getType());
        Pokemon updatedPokemon = pokemonRepo.save(pokemon);
        return mapToDao(updatedPokemon);
    }

    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = pokemonRepo.findById(id).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon could not be deleted"));
        pokemonRepo.delete(pokemon);
    }

    private PokemonDao mapToDao(Pokemon pokemon) {
        PokemonDao pokemonDao = new PokemonDao();
        pokemonDao.setId(pokemon.getId());
        pokemonDao.setName(pokemon.getName());
        pokemonDao.setType(pokemon.getType());
        return pokemonDao;
    }

    private Pokemon mapToEntity(PokemonDao pokemonDao) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDao.getName());
        pokemon.setType(pokemonDao.getType());
        return pokemon;
    }

}
