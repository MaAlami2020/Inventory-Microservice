package com.example.webapp1a.model.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

//import org.hibernate.annotations.Type;

import java.sql.Blob;

@Entity
@Table(name = "tbl_user")
public class User {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String encodedPassword;

    @Column(name = "rol")
    private String rol;

    private String passwordConfirmation;

    @Lob
    //@Type(type = "org.hibernate.type.ImageType")
    @Column(name = "image")
    private Blob avatar;

    public User(){}

    public User(String email, String encodedPassword, String roles){
        this.email=email;
        this.encodedPassword=encodedPassword;
        this.rol=roles;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setEncodedPassword(String password){
        this.encodedPassword = password;
    }

    public String getEncodedPassword(){
        return encodedPassword;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(String rol){
        this.rol = rol;
    }

    public void setConfirmationPassword(String passwordConfirmation){
        this.passwordConfirmation=passwordConfirmation;
    }

    public String getConfirmationPassword(){
        return passwordConfirmation;
    }

    public void setImageFile(Blob image){
        this.avatar = image;
    }

    public Blob getImageFile(){
        return avatar;
    }
}
