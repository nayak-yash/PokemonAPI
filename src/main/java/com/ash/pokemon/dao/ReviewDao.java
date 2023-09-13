package com.ash.pokemon.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDao {
    private int id;
    private String title;
    private String content;
    private int stars;
}
