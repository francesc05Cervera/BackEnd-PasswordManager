package com.francesco.passwordmanager.DTO;

public class CredenzialeResponse {

    private Long id;
    private String nomeSito;
    private String urlSito;
    private String username;
    private String password;

    public CredenzialeResponse() {}

    public CredenzialeResponse(Long id, String nomeSito, String urlSito, String username, String password) {
        this.id = id;
        this.nomeSito = nomeSito;
        this.urlSito = urlSito;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getNomeSito() {
        return nomeSito;
    }

    public String getUrlSito() {
        return urlSito;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
