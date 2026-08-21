package com.francesco.passwordmanager.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.francesco.passwordmanager.DTO.LoginRequest;
import com.francesco.passwordmanager.DTO.LoginResponse;
import com.francesco.passwordmanager.DTO.RegisterRequest;
import com.francesco.passwordmanager.DTO.meResponse;
import com.francesco.passwordmanager.Exception.LoginFallitoException;
import com.francesco.passwordmanager.Exception.NonTrovatoException;
import com.francesco.passwordmanager.Exception.RegistrazioneFallitaException;
import com.francesco.passwordmanager.Exception.TokenNonValidoException;
import com.francesco.passwordmanager.entity.Utente;
import com.francesco.passwordmanager.Repository.UtenteRepo;
import com.francesco.passwordmanager.Security.JWTservice;

public class UtenteService 
{
    private final UtenteRepo utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTservice jwtService;
    
    public UtenteService(UtenteRepo utenteRepository, PasswordEncoder passwordEncoder, JWTservice jwtService) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) throws RegistrazioneFallitaException 
    {
        Utente exist = utenteRepository.findByEmail(request.getEmail());
        if (exist == null) { throw new RegistrazioneFallitaException("Email già registrata"); }

        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setHashPassword(passwordEncoder.encode(request.getPassword()));
        utenteRepository.save(utente);
    }

    public LoginResponse login(LoginRequest request) throws LoginFallitoException 
    {
        Utente utente = utenteRepository.findByEmail(request.getEmail());
        
        if(utente == null)
        {
            throw new LoginFallitoException("Email non registrata");
        }

        if(!passwordEncoder.matches(request.getPassword(), utente.getHashPassword()))
        {
            throw new LoginFallitoException("Password errata");
        }

        // Generate and return JWT token
        String token = jwtService.generateToken(utente);
        return new LoginResponse(token);
    }

    public meResponse getMe(String token) throws NonTrovatoException, TokenNonValidoException 
    {
        String email = jwtService.extractMail(token);
        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) {
            throw new NonTrovatoException("Token non valido o utente non trovato");
        }

        if (!jwtService.isTokenValid(token, utente)) {
            throw new TokenNonValidoException("Token non valido");
        }

        return new meResponse(utente.getEmail());
    }



}


