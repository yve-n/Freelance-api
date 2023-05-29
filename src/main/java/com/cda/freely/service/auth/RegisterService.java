package com.cda.freely.service.auth;

import com.cda.freely.dto.address.AddressDTO;
import com.cda.freely.dto.company.CompanyDTO;
import com.cda.freely.dto.user.UserDTO;
import com.cda.freely.entity.*;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.exception.ResourceNotFoundException;
import com.cda.freely.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegisterService {
    private FamilyService familyService;
    private TagService tagService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private CompanyService companyService;
    private AddressService addressService;
    private final Logger logger = LoggerFactory.getLogger(RegisterService.class);
    @Autowired
    public RegisterService(FamilyService familyService,
                           TagService tagService,
                           UserService userService,
                           PasswordEncoder passwordEncoder,
                           CompanyService companyService,
                           AddressService addressService)
    {
        this.familyService = familyService;
        this.tagService = tagService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
        this.addressService = addressService;
    }
    @Transactional
    public User CreateUser(UserDTO userDTO){
        logger.error("user--+++++++++++++++++++-> {}", userDTO.toString());

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setGender(userDTO.getGender());
        user.setRole(User.Role.ROLE_USER);
        user.setUserAccountState((User.Status.PENDING));
        user.setUserAvailability(User.Availability.YES);
        user.setCreatedAt(new Date());

        Family family = familyService.findById(userDTO.getFamilyId())
                .orElseThrow(() -> new NotFoundException("Family not found"));
        user.setFamily(family);

        logger.warn("user//////////////////////> {}", family.toString());

        List<Tag> tags = tagService.findTags(userDTO.getTagIds());

        user.setTags (tags);

        User savedUser = userService.saveUser(user);

        logger.error("usercreated---------------------------------> {}");

        List<Company> companies = new ArrayList<>();
        for(CompanyDTO companyDto : userDTO.getCompanies()) {
            Company company = new Company();
            company.setName(companyDto.getName());
            company.setSiren(companyDto.getSiren());
            company.setTva(companyDto.isTva());
            company.setNumber(companyDto.getNumber());
            company.setCompanyState(Company.Status.ACTIVE);
            company.setUser(savedUser);
            Company savedCompany = companyService.saveCompany(company);
            logger.error("===============---********************---> {}");

            List<Address> addresses = new ArrayList<>();
            for(AddressDTO addressDto : companyDto.getAddresses()) {
                Address address = new Address();
                address.setAddress(addressDto.getAddress());
                address.setCity(addressDto.getCity());
                address.setZipCode(addressDto.getZipCode());
                address.setCountry(addressDto.getCountry());
                address.setCompany(savedCompany);
//                company.addAddress(address);

                Address savedAddress = addressService.saveAddress(address);
                addresses.add(savedAddress);
                logger.error("===============------> {}");
            }

            savedCompany.setAddresses(addresses);
           savedUser.addCompany(company);
           companies.add(savedCompany);
        }

        savedUser.setCompanies(companies);
//        return savedUser;
            return userService.saveUser(savedUser);
    }
}
