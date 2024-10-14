package com.fatecrl.viagens.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Location implements Serializable{

    private Long id;
    private String name;
    private String nickname;
    private String address;
    private String city;
    private BigDecimal state;
    private String country;
    
    public Location(){
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public BigDecimal getState() {
        return state;
    }
    public void setState(BigDecimal state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }


}
