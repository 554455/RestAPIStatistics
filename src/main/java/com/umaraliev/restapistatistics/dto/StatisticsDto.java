package com.umaraliev.restapistatistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umaraliev.restapistatistics.model.CompanyEntity;
import com.umaraliev.restapistatistics.model.StatisticsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsDto {


    private  Long id;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("previousVolume")
    private Integer previousVolume;

    @JsonProperty("latestPrice")
    private Double latestPrice;

    public StatisticsEntity toEntity() {
        return new StatisticsEntity(id,companyName, previousVolume, latestPrice);
    }

    public CompanyDto fromEntity(CompanyEntity company) {
        return new CompanyDto(id, company.getSymbol());
    }
}
