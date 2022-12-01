package com.umaraliev.restapistatistics;

import com.umaraliev.restapistatistics.model.Company;
import com.umaraliev.restapistatistics.service.CompanyService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RestapiStatisticsApplicationTests {

    private final CompanyService companyService;

    RestapiStatisticsApplicationTests(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Test
    void beforeAll() {
        companyService.save();
    }

    @Test
    void name() {
        List<Company> companies = companyService.listAll();
        companies.stream().forEach(System.out::println);
    }
}
