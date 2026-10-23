package com.travel.service;

import com.travel.model.User;
import com.travel.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Map<String, Long> tokens = new ConcurrentHashMap<>(); // token -> userId

    public AuthService(UserRepository userRepo) { this.userRepo = userRepo; }

    public User register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.length() < 4)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username required and password needs 4+ characters");
        if (userRepo.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        return userRepo.save(new User(username, encoder.encode(password)));
    }

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        String token = UUID.randomUUID().toString();
        tokens.put(token, user.getId());
        return token;
    }

    /** Reads "Authorization: Bearer <token>" and returns the logged-in user. */
    public User getUser(String header) {
        String token = header == null ? "" : header.replace("Bearer ", "");
        Long id = tokens.get(token);
        if (id == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please log in");
        return userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please log in"));
    }
}
