package com.example.webapp1a.security.jwt;

public class AuthResponse {

    private Status status;
    private String message;

    public enum Status{
        SUCCESS
    }

    public AuthResponse(){}

    public AuthResponse(Status status, String msg){
        this.status = status;
        this.message = msg;
    }

    public Status getStatus(){
        return status;
    }
    
    public void setStatus(Status status){
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResponse [status=" + status + ", message=" + message + "]";
    }
}
