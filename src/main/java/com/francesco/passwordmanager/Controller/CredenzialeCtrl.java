package com.francesco.passwordmanager.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.francesco.passwordmanager.Service.CredenzialeService;
import com.francesco.passwordmanager.DTO.CreaCredenzialeRequest;
import com.francesco.passwordmanager.Exception.CreazioneFallitaException;
import com.francesco.passwordmanager.Exception.NonTrovatoException;
import com.francesco.passwordmanager.Exception.TokenNonValidoException;
import com.francesco.passwordmanager.DTO.CredenzialeResponse;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/credenziali")
@CrossOrigin(origins = "https://passwordmanager-5w59.onrender.com")
public class CredenzialeCtrl 
{
    private final CredenzialeService credenzialeService;

    public CredenzialeCtrl(CredenzialeService credenzialeService) 
    {
        this.credenzialeService = credenzialeService;
    }

    @PostMapping
    public ResponseEntity<String> createCredenziale(@RequestHeader("Authorization") String token,
                                                    @Valid @RequestBody CreaCredenzialeRequest request) 
    {
        try 
        {
            credenzialeService.creaCredenziale(token, request);
            return ResponseEntity.ok("Credenziale creata con successo");
        } 
        catch (CreazioneFallitaException e) 
        {
            return ResponseEntity.badRequest().body("Errore nella creazione della credenziale: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getCredenziali(@RequestHeader("Authorization") String token) {
        try {
            List<CredenzialeResponse> credenziali = credenzialeService.getCredenziali(token);
            return ResponseEntity.ok(credenziali);
        } catch (TokenNonValidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCredenziale(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long id,
                                                     @Valid @RequestBody CreaCredenzialeRequest request) {
        try {
            return ResponseEntity.ok(credenzialeService.modificaCredenziale(token, id, request));
        } catch (TokenNonValidoException | NonTrovatoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCredenziale(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long id) {
        try {
            credenzialeService.eliminaCredenziale(token, id);
            return ResponseEntity.ok("Credenziale eliminata con successo");
        } catch (TokenNonValidoException | NonTrovatoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}