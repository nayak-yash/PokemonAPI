package com.ash.pokemon.controllers;

import com.ash.pokemon.dao.AuthResponseDao;
import com.ash.pokemon.dao.LoginDao;
import com.ash.pokemon.dao.RegisterDao;
import com.ash.pokemon.models.Role;
import com.ash.pokemon.models.UserEntity;
import com.ash.pokemon.repository.RoleRepository;
import com.ash.pokemon.repository.UserRepository;
import com.ash.pokemon.security.JWTGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDao registerDao) {
        if(userRepo.existsByUsername(registerDao.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDao.getUsername());
        user.setPassword(passwordEncoder.encode(registerDao.getPassword()));
        Role roles = roleRepo.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDao> login(@RequestBody LoginDao loginDao) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDao.getUsername(), loginDao.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponseDao(token));
    }

}
