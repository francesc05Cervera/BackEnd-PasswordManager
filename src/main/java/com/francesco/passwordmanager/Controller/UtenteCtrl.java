package com.francesco.passwordmanager.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;

import com.francesco.passwordmanager.Exception.*;
import com.francesco.passwordmanager.DTO.RegisterRequest;
import com.francesco.passwordmanager.DTO.meResponse;
import com.francesco.passwordmanager.DTO.LoginRequest;
import com.francesco.passwordmanager.DTO.LoginResponse;
import com.francesco.passwordmanager.Service.UtenteService;

@RestController
@RequestMapping("/api/utente")
@CrossOrigin(origins = "*")
public class UtenteCtrl 
{
    private final UtenteService utenteService;

    public UtenteCtrl(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest request)
    {
        try
        {
            utenteService.register(request); 
            return ResponseEntity.ok("Registrazione avvenuta con successo");

        } catch(RegistrazioneFallitaException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest request)
    {
       try
        {
            LoginResponse res = utenteService.login(request);
            return ResponseEntity.ok(res);

        } catch(LoginFallitoException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Object> me(@RequestHeader("Authorization") String token)
    {
        try {
            meResponse response = utenteService.getMe(token);
            return ResponseEntity.ok(response);
        } catch (NonTrovatoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (TokenNonValidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
