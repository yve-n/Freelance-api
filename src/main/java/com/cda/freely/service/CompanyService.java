package com.cda.freely.service;


import com.cda.freely.entity.Company;
import com.cda.freely.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private CompanyRepository companyRepository;
    @Autowired
    public CompanyService(CompanyRepository companyRepository) {

        this.companyRepository = companyRepository;
    }

    /**
     * find a company
     * @param id
     * @return Company
     */
    public Optional<Company> findCompanyById(Long id){
        return companyRepository.findById(id);
    }

    /**
     * save a company
     * @param company
     * @return Company
     */
    public Company saveCompany(Company company){
        if(company.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return companyRepository.save(company);
    }

}
