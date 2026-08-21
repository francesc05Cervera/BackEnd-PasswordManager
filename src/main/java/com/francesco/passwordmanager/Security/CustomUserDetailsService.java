package com.francesco.passwordmanager.Security;

import com.francesco.passwordmanager.Repository.UtenteRepo;
import com.francesco.passwordmanager.entity.Utente;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepo utenteRepo;

    public CustomUserDetailsService(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Utente utente = utenteRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utente non trovato: " + username));

        return User.withUsername(utente.getUsername())
                .password(utente.getPassword())
                .roles("USER")
                .build();
    }
}
