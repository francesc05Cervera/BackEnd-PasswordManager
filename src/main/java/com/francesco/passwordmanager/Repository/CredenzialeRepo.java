package com.francesco.passwordmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.francesco.passwordmanager.entity.Credenziale;
import java.util.List;

@Repository
public interface CredenzialeRepo extends JpaRepository<Credenziale, Long> 
{
    List<Credenziale> findByutenteId(Long utenteId);
    void deleteByid(Long id);

}