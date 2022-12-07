package com.umaraliev.restapistatistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.umaraliev.restapistatistics.model.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    private Long id;

    @JsonProperty("symbol")
    private String symbol;

    public CompanyEntity toEntity() {
        return new CompanyEntity(id, symbol);
    }

    public static CompanyDto fromEntity(CompanyEntity company) {
        return new CompanyDto(company.getId(), company.getSymbol());
    }
}
