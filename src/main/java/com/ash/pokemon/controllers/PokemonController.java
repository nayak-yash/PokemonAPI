package com.ash.pokemon.controllers;

import com.ash.pokemon.dao.PokemonDao;
import com.ash.pokemon.dao.PokemonResponse;
import com.ash.pokemon.models.Pokemon;
import com.ash.pokemon.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {

    private PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(pokemonService.getAllPokemon(pageNo, pageSize));
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDao> pokemonDetail(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @PostMapping("pokemon/create")
    public ResponseEntity<PokemonDao> createPokemon(@RequestBody PokemonDao pokemonDao) {
        return ResponseEntity.ok(pokemonService.createPokemonDao(pokemonDao));
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDao> update(@RequestBody PokemonDao pokemonDao, @PathVariable int id) {
        return ResponseEntity.ok(pokemonService.updatePokemon(pokemonDao, id));
    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable int id) {
        pokemonService.deletePokemon(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
