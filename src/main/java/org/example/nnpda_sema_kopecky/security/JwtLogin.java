package org.example.nnpda_sema_kopecky.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtLogin {
    private String username;
    private String password;
}
