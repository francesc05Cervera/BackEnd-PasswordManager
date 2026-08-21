package com.francesco.passwordmanager.DTO;

public class meResponse 
{
    public String email;
    
    public meResponse() {}
    public meResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
