package com.ash.pokemon.dao;

import lombok.Data;

import java.util.List;

@Data
public class PokemonResponse {
    private List<PokemonDao> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
