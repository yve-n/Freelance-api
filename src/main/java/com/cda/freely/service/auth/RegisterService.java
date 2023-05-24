package com.cda.freely.service.auth;

import com.cda.freely.dto.address.AddressDTO;
import com.cda.freely.dto.company.CompanyDTO;
import com.cda.freely.dto.user.UserDTO;
import com.cda.freely.entity.*;
import com.cda.freely.exception.ResourceNotFoundException;
import com.cda.freely.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User CreateUser(UserDTO userDTO){
        logger.warn("user--+++++++++++++++++++-> {}", userDTO.toString());

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
                .orElseThrow(() -> new RuntimeException("Family not found"));
        user.setFamily(family);

        logger.warn("user//////////////////////> {}", family.toString());

        Set<Tag> Tags = userDTO.getTagIds().stream()
                .map(tagId -> tagService.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId)))
                .collect(Collectors.toSet());

        user.setTags(Tags);
//        userDTO.getTagIds().forEach(tagId -> tagService.addUserToTag(tagId, user));

        User savedUser = userService.saveUser(user);
        logger.warn("usercreated---------------------------------> {}", savedUser.toString());

        List<Company> companies = new ArrayList<>();
        for(CompanyDTO companyDto : userDTO.getCompanies()) {
            Company company = new Company();
            company.setName(companyDto.getName());
            company.setSiren(companyDto.getSiren());
            company.setTva(companyDto.isTva());
            company.setNumber(companyDto.getNumber());
            company.setCompanyState(Company.Status.ACTIVE);
            logger.error("===============---********************---> {}", savedUser.toString());
            company.setUser(savedUser);
            Company savedCompany = companyService.saveCompany(company);

            List<Address> addresses = new ArrayList<>();
            for(AddressDTO addressDto : companyDto.getAddresses()) {
                Address address = new Address();
                address.setAddress(addressDto.getAddress());
                address.setCity(addressDto.getCity());
                address.setZipCode(addressDto.getZipCode());
                address.setCountry(addressDto.getCountry());
                address.setCompany(savedCompany);

                Address savedAddress = addressService.saveAddress(address);
                addresses.add(savedAddress);
            }

            savedCompany.setAddresses(addresses);
           // userService.saveCompany(savedCompany);
           companies.add(savedCompany);
        }

        savedUser.setCompanies(companies);
            return savedUser;
    }
}
