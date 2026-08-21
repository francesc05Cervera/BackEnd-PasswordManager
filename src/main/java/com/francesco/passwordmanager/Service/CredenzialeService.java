package com.francesco.passwordmanager.Service;
import org.springframework.stereotype.Service;
import com.francesco.passwordmanager.entity.Credenziale;
import com.francesco.passwordmanager.entity.Utente;
import com.francesco.passwordmanager.Repository.CredenzialeRepo;
import com.francesco.passwordmanager.Repository.UtenteRepo;
import com.francesco.passwordmanager.Security.JWTservice;
import com.francesco.passwordmanager.Security.CredentialEncryptionService;
import com.francesco.passwordmanager.DTO.CreaCredenzialeRequest;
import com.francesco.passwordmanager.DTO.CredenzialeResponse;
import com.francesco.passwordmanager.Exception.CreazioneFallitaException;
import com.francesco.passwordmanager.Exception.TokenNonValidoException;
import com.francesco.passwordmanager.Exception.NonTrovatoException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CredenzialeService 
{
    private final CredenzialeRepo credenzialeRepository; 
    private final JWTservice jwtService; // Assuming you have a JWTservice for token validation
    private final UtenteRepo utenteRepository; // Assuming you have a UtenteRepository for user management
    private final CredentialEncryptionService credentialEncryptionService;

    public CredenzialeService(CredenzialeRepo credenzialeRepository, JWTservice jwtService,
                              UtenteRepo utenteRepository,
                              CredentialEncryptionService credentialEncryptionService) 
    {
        this.credenzialeRepository = credenzialeRepository;
        this.jwtService = jwtService;
        this.utenteRepository = utenteRepository;
        this.credentialEncryptionService = credentialEncryptionService;
    }

    public Credenziale creaCredenziale(String token,CreaCredenzialeRequest request) throws CreazioneFallitaException
    {
        try 
        {
            if (checkToken(token)) 
            {
                Credenziale credenziale = new Credenziale();
                credenziale.setNomeSito(request.getNomeSito());
                credenziale.setUrlSito(request.getUrlSito());
                credenziale.setUsername(request.getUsername());
                credenziale.setEncryptedPassword(credentialEncryptionService.encrypt(request.getPassword()));
                credenziale.setUtenteId(utenteRepository.findByEmail(jwtService.extractMail(token)).getId());
                return credenzialeRepository.save(credenziale);
            } 
            else 
            {
                throw new CreazioneFallitaException("Token non valido");
            }
        } 
        catch (TokenNonValidoException e) 
        {
            throw new CreazioneFallitaException("Token non valido");
        }
    }

    public List<CredenzialeResponse> getCredenziali(String token) throws TokenNonValidoException {
        Long utenteId = getUtenteIdFromToken(token);
        return credenzialeRepository.findByUtenteId(utenteId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CredenzialeResponse modificaCredenziale(String token, Long id, CreaCredenzialeRequest request)
            throws TokenNonValidoException, NonTrovatoException {
        Long utenteId = getUtenteIdFromToken(token);
        Optional<Credenziale> credenzialeOptional = credenzialeRepository.findByIdAndUtenteId(id, utenteId);

        if (credenzialeOptional.isEmpty()) {
            throw new NonTrovatoException("Credenziale non trovata");
        }

        Credenziale credenziale = credenzialeOptional.get();
        credenziale.setNomeSito(request.getNomeSito());
        credenziale.setUrlSito(request.getUrlSito());
        credenziale.setUsername(request.getUsername());
        credenziale.setEncryptedPassword(credentialEncryptionService.encrypt(request.getPassword()));
        return toResponse(credenzialeRepository.save(credenziale));
    }

    public void eliminaCredenziale(String token, Long id)
            throws TokenNonValidoException, NonTrovatoException {
        Long utenteId = getUtenteIdFromToken(token);
        Optional<Credenziale> credenzialeOptional = credenzialeRepository.findByIdAndUtenteId(id, utenteId);

        if (credenzialeOptional.isEmpty()) {
            throw new NonTrovatoException("Credenziale non trovata");
        }

        credenzialeRepository.delete(credenzialeOptional.get());
    }

    


    
   private boolean checkToken(String token) throws TokenNonValidoException {
    try {
        String email = jwtService.extractMail(token);
        if (email == null || email.isBlank()) {
            throw new TokenNonValidoException("Email non valida nel token");
        }

        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) {
            throw new TokenNonValidoException("Utente non trovato");
        }

        if (!jwtService.isTokenValid(token, utente)) {
            throw new TokenNonValidoException("Token non valido");
        }
    } catch (TokenNonValidoException exception) {
        throw exception;
    } catch (RuntimeException exception) {
        exception.printStackTrace();
        throw new TokenNonValidoException("Token non valido: " + exception.getMessage());
    }
    return true;
}

    private Long getUtenteIdFromToken(String token) throws TokenNonValidoException {
        if (!checkToken(token)) {
            throw new TokenNonValidoException("Token non valido");
        }

        return utenteRepository.findByEmail(jwtService.extractMail(token)).getId();
    }

    private CredenzialeResponse toResponse(Credenziale credenziale) {
        return new CredenzialeResponse(
                credenziale.getId(),
                credenziale.getNomeSito(),
                credenziale.getUrlSito(),
                credenziale.getUsername(),
                credentialEncryptionService.decrypt(credenziale.getEncryptedPassword()));
    }
}