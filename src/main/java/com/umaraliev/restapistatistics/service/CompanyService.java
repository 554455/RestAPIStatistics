package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.Company;
import com.umaraliev.restapistatistics.repository.CompanyRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    private RestTemplate restTemplate = new RestTemplate();


    public void save() {
        String urlSymbols = "https://sandbox.iexapis.com/stable/ref-data/symbols?token=Tpk_ee567917a6b640bb8602834c9d30e571";

        ResponseEntity<List<Company>> rateResponse =
                restTemplate.exchange(urlSymbols,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {});

        List<Company> companyList = rateResponse.getBody();
        for (Company company : companyList) {
            companyRepository.save(company);
        }
    }

    public List<Company> listAll() {
        return companyRepository.findAll()
                .stream()
                .filter(c -> c != null && c.getSymbol() != null)
                .collect(Collectors.toList());
    }

}
