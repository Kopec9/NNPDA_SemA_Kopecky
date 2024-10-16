package org.example.nnpda_sema_kopecky.security;

import lombok.Getter;

@Getter
public class JwtResponse {
    private String token;
    public JwtResponse(String token) {
        this.token = token;
    }
}
