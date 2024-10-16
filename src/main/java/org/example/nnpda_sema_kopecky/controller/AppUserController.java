package org.example.nnpda_sema_kopecky.controller;

import jakarta.validation.Valid;
import org.example.nnpda_sema_kopecky.dto.NewAppUserDto;
import org.example.nnpda_sema_kopecky.dto.NewPasswordDto;
import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.example.nnpda_sema_kopecky.security.JwtLogin;
import org.example.nnpda_sema_kopecky.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/app-user")
public class AppUserController {
    @Autowired
    private final AppUserRepository appUserRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/all")
    public Collection<AppUser> getAppUsers() {
        return appUserService.getAllAppUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppUserById(@PathVariable int id) {
        AppUser appUser = appUserService.findById(id);

        if (appUser != null) {
            return ResponseEntity.ok(appUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id " + id + " was not found.");
        }
    }

    @PostMapping("/new")
    public String createAppUser(@Valid @RequestBody NewAppUserDto newAppUser,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            appUserService.createAppUser(newAppUser);
            return "User successfully created.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while adding a user.";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtLogin authenticationRequest) {
        return appUserService.login(authenticationRequest);
    }

    @PutMapping("/pass/{id}")
    public String updateAppUserPassword(@PathVariable int id, @RequestBody NewPasswordDto newPass,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            appUserService.changePassword(id, newPass);
            return "Password changed successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Incorrect original password.";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAppUser(@PathVariable int id) {

        try {
            appUserService.deleteAppUser(id);
            return "User successfully deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while deleting the user.";
        }
    }
}
