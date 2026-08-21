package com.francesco.passwordmanager.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "credenziali")
public class Credenziale 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeSito;

    @Column(nullable = false)
    private String urlSito;
    
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, name = "encrypted_password")
    private String encryptedPassword;

    @Column(nullable = false)
    private Long utenteId;
    
    public Credenziale() {}

    public Credenziale(String nomeSito, String urlSito, String username, String encryptedPassword) {
        this.nomeSito = nomeSito;
        this.urlSito = urlSito;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public Long getId() {
        return id;
    }

    public String getNomeSito() {
        return nomeSito;
    }

    public void setNomeSito(String nomeSito) {
        this.nomeSito = nomeSito;
    }

    public String getUrlSito() {
        return urlSito;
    }

    public void setUrlSito(String urlSito) {
        this.urlSito = urlSito;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Long getUtenteId() {
        return utenteId;
    }
    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }
    
}
