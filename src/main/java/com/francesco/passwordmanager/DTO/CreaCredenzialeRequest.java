package com.francesco.passwordmanager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreaCredenzialeRequest 
{
    @NotBlank
    @NotNull
    private String nomeSito;
    @NotBlank
    @NotNull
    private String urlSito;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;

    public CreaCredenzialeRequest() {}

    public CreaCredenzialeRequest(String nomeSito, String urlSito, String username, String password) {
        this.nomeSito = nomeSito;
        this.urlSito = urlSito;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
