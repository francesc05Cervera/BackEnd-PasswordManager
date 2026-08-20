package com.francesco.passwordmanager.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; 

    @Column(nullable = false)
    private String hashPassword;

    public Utente() {}
    public Utente(String email, String hashPassword) {
        this.email = email;
        this.hashPassword = hashPassword;
    }

    /* Costruttori */
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    /* Getter e Setter */
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) 
    {
        this.hashPassword = hashPassword;
    }
}