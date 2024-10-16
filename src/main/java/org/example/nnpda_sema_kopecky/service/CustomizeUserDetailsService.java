package org.example.nnpda_sema_kopecky.service;

import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CustomizeUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
