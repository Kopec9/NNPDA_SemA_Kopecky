package org.example.nnpda_sema_kopecky.repository;

import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String username);

    AppUser findById(int id);

    AppUser findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
