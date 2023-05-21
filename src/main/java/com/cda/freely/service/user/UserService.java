package com.cda.freely.service.user;

import com.cda.freely.entity.Address;
import com.cda.freely.entity.Company;
import com.cda.freely.entity.User;
import com.cda.freely.repository.AddressRepository;
import com.cda.freely.repository.CompanyRepository;
import com.cda.freely.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
     private UserRepository userRepository;
     private CompanyRepository companyRepository;
     private AddressRepository addressRepository;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    public Optional<User> findById(Long id){ return userRepository.findById(id);}
    public Optional<User> findUserByMail(String email){ return Optional.ofNullable(userRepository.findByEmail(email));}
    public Optional<Company>findCompanyById(Long id){
        return companyRepository.findById(id);
    }
    public Optional<Address>findAddressById(Long id){return addressRepository.findById(id);}
    public Company saveCompany(Company company){
        if(company.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return companyRepository.save(company);
    }
    public Address saveAddress(Address address){return addressRepository.save(address);}
    public User saveUser(User user){return userRepository.save(user);}
}
