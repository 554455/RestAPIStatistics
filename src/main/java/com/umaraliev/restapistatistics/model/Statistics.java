package com.umaraliev.restapistatistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "statistics")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics implements Comparator<Statistics>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "previous_volume")
    private Integer previousVolume;

    @Column(name = "latest_price")
    private Double latestPrice;

    public Statistics(Long id, String companyName, Integer previousVolume, Double latestPrice) {
        this.id = id;
        this.companyName = companyName;
        this.previousVolume = previousVolume;
        this.latestPrice = latestPrice;
    }

    public Statistics() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getPreviousVolume() {
        return previousVolume;
    }

    public void setPreviousVolume(Integer previousVolume) {
        this.previousVolume = previousVolume;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }


    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", previousVolume=" + previousVolume +
                ", latestPrice=" + latestPrice +
                '}';
    }

    @Override
    public int compare(Statistics o1, Statistics o2) {
        if (o1.getLatestPrice() == null || o2.getLatestPrice() == null){
            return -1;
        }
        return 0;
    }
}
