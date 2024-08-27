package com.example.webapp1a.model.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import java.sql.Blob;

@Entity
@Table(name = "tbl_user")
public class User {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@Column(name = "name")
    //private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private String confirmationPassword;

    @Lob
    @Column(name = "image")
    private Blob avatar;

    public User(){}

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    /*public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }*/

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setConfirmationPassword(String confirmationPassword){
        this.confirmationPassword = confirmationPassword;
    }

    public String getConfirmationPassword(){
        return confirmationPassword;
    }

    public void setAvatar(Blob image){
        this.avatar = image;
    }

    public Blob getAvatar(){
        return avatar;
    }
}
