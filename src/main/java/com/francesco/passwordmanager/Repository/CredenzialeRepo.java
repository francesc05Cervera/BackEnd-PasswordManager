package com.francesco.passwordmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.francesco.passwordmanager.entity.Credenziale;
import java.util.List;
import java.util.Optional;

@Repository
public interface CredenzialeRepo extends JpaRepository<Credenziale, Long> 
{
    List<Credenziale> findByUtenteId(Long utenteId);
    Optional<Credenziale> findByIdAndUtenteId(Long id, Long utenteId);

}