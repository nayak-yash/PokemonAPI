package com.ash.pokemon.dao;

import lombok.Data;

@Data
public class AuthResponseDao {

    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDao(String accessToken) {
        this.accessToken = accessToken;
    }

}
