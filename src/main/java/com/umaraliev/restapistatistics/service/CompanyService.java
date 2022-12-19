package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.CompanyEntity;
import com.umaraliev.restapistatistics.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RestTemplate restTemplate;

    @Value("${iex.api.host}")
    private String iexApiHost;

    @Value("${iex.api.key}")
    private String iexApiKey;

    ExecutorService fixedPool = Executors.newFixedThreadPool(8);

    @Async
    public void saveCompanyDetails(List<CompanyEntity> companyEntities) {
        if (!CollectionUtils.isEmpty(companyEntities)) {
            companyRepository.saveAll(companyEntities);
        }
    }

    public List<CompanyEntity> getCompanyEntityAll(){
        List<CompanyEntity> companyEntities = new LinkedList<>();
            ResponseEntity<List<CompanyEntity>> rateResponse =
                    restTemplate.exchange(iexApiHost + iexApiKey,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<CompanyEntity>>() {
                            });

            companyEntities = rateResponse.getBody();
            saveCompanyDetails(companyEntities);
        return companyEntities;
    }
    public List<CompanyEntity> listAll() {
        return companyRepository.findAll()
                .stream()
                .filter(c -> c != null && c.getSymbol() != null)
                .collect(Collectors.toList());
    }

    public CompanyEntity getOneCompanyEntity(Long id) {
        return companyRepository.findById(id).get();
    }
}
