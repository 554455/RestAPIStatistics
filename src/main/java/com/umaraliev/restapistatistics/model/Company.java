package com.umaraliev.restapistatistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "company")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    public Company(Long id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public Company() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
