package com.ash.pokemon.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstants {

    public static final long JWT_EXPIRATION = 70000;
    public static final String JWT_SECRET = "secret";
    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

}
