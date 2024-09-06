package com.example.webapp1a.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_admin")
public class Admin {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private String confirmationPassword;
    
    @Column(name = "rol")
    private String rol;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEncodedPassword(){
        return password;
    }

    public void setEncodedPassword(String password){
        this.password = password;
    }

    public String getConfirmationPassword(){
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPass){
        this.confirmationPassword = confirmationPass;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol = rol;
    }

    
}
