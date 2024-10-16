package org.example.nnpda_sema_kopecky.service;

import org.example.nnpda_sema_kopecky.dto.NewAppUserDto;
import org.example.nnpda_sema_kopecky.dto.NewPasswordDto;
import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.example.nnpda_sema_kopecky.security.JwtLogin;
import org.example.nnpda_sema_kopecky.security.JwtToken;
import org.example.nnpda_sema_kopecky.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Collection<AppUser> getAllAppUsers(){
        return appUserRepository.findAll();
    }
    public void createAppUser(NewAppUserDto newAppUser){

        if (appUserRepository.existsByEmail(newAppUser.getEmail()) || appUserRepository.existsByUsername(newAppUser.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(newAppUser.getUsername());
        newUser.setEmail(newAppUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(newAppUser.getPassword()));

        appUserRepository.save(newUser);
    }

    public void changePassword(int id, NewPasswordDto newPass) {
        AppUser existingUser = appUserRepository.findById(id);
        if (passwordEncoder.matches(newPass.getOldPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(newPass.getNewPassword()));
            appUserRepository.save(existingUser);
        } else {
            throw new RuntimeException("The old password is incorrect");
        }
    }

    public ResponseEntity<?> login(JwtLogin authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token = jwtToken.generateToken(authentication);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
    }

    public void deleteAppUser(int id){
        if (appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public AppUser findById(int id){
        return appUserRepository.findById(id);
    }
}
