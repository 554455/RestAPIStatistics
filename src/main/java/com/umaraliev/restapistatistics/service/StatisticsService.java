package com.umaraliev.restapistatistics.service;

import com.umaraliev.restapistatistics.model.Company;
import com.umaraliev.restapistatistics.model.Statistics;
import com.umaraliev.restapistatistics.repository.StatisticsRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    private final CompanyService companyService;

    public StatisticsService(StatisticsRepository statisticsRepository, CompanyService companyService) {
        this.statisticsRepository = statisticsRepository;
        this.companyService = companyService;
    }

    private RestTemplate restTemplate = new RestTemplate();

    public void save() {
        statisticsRepository.deleteAll();
        List<Company> companies = companyService.listAll();
        for (Company company : companies) {
            String urlQuote = "https://sandbox.iexapis.com/stable/stock/" + company.getSymbol() + "/quote?token=Tpk_ee567917a6b640bb8602834c9d30e571";

            Statistics statistics = restTemplate
                    .getForObject(urlQuote, Statistics.class);
                statisticsRepository.save(statistics);
        }
    }

    public List<Statistics> getExpensiveStocks() {
        return statisticsRepository.findAll()
                .stream()
                .filter(s -> s != null && s.getPreviousVolume() != null)
                .sorted(Comparator.comparingInt(Statistics::getPreviousVolume).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Statistics> getChangedPrices() {
        return statisticsRepository.findAll()
                .stream()
                .filter(s -> s != null && s.getLatestPrice() != null)
                .sorted(Comparator.comparingDouble(Statistics::getLatestPrice).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }


}
