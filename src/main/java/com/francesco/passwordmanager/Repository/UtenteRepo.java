package com.francesco.passwordmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.francesco.passwordmanager.entity.Utente;


@Repository
public interface UtenteRepo extends JpaRepository<Utente, Long> 
{
    Utente findByEmail(String email);
    Utente findByid(Long id);
    
}
