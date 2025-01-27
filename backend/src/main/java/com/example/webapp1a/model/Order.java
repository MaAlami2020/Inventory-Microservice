package com.example.webapp1a.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Column(name="totalCost")
    private Double totalCost;

    @Column(name="date")
    private Date creationDate;

    @Column(name="state")
    private State state;

    public enum State{
        PENDING, IN_PROGRESS, CONFIRMED, CANCELLED
    }

    public Order(){}

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void setTotalCost(Double totalCost){
        this.totalCost = totalCost;
    }

    public Double getTotalCost(){
        return totalCost;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
