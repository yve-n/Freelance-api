package com.cda.freely.service.auth;

import com.cda.freely.dto.address.AddressDTO;
import com.cda.freely.dto.company.CompanyDTO;
import com.cda.freely.dto.user.UserDTO;
import com.cda.freely.entity.*;
import com.cda.freely.exception.ResourceNotFoundException;
import com.cda.freely.repository.*;
import com.cda.freely.service.FamilyService;
import com.cda.freely.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegisterService {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private AddressRepository addressRepository;
    private FamilyRepository familyRepository;
    private FamilyService familyService;
    private TagService tagService;
    private PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    public RegisterService(UserRepository userRepository,
                           CompanyRepository companyRepository,
                           AddressRepository addressRepository,
                           FamilyRepository familyRepository,
                           FamilyService familyService,
                            PasswordEncoder passwordEncoder,
                           TagService tagService
                          ) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.familyRepository = familyRepository;
        this.familyService = familyService;
        this.passwordEncoder = passwordEncoder;
        this.tagService = tagService;
    }

    public Optional<User> findByMail(String email){ return Optional.ofNullable(userRepository.findByEmail(email));}
    public User CreateUser(UserDTO userDTO){
        logger.warn("user----------------> {}", userDTO.toString());

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

        logger.warn("user----------------> {}", family);

        Set<Tag> Tags = userDTO.getTagIds().stream()
                .map(tagId -> tagService.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId)))
                .collect(Collectors.toSet());

        logger.warn("user----------------> {}", Tags);
        user.setTags(Tags);

        // We assume that CompanyDTO list has been included in UserDTO
        List<Company> companies = new ArrayList<>();
        for(CompanyDTO companyDto : userDTO.getCompanies()) {
            Company company = new Company();
            company.setName(companyDto.getName());
            company.setSiren(companyDto.getSiren());
            company.setTva(companyDto.isTva());
            company.setCompanyState(Company.Status.ACTIVE);
            company.setUser(user);

            List<Address> addresses = new ArrayList<>();
            for(AddressDTO addressDto : companyDto.getAddresses()) {
                Address address = new Address();
                address.setAddress(addressDto.getAddress());
                address.setCity(addressDto.getCity());
                address.setZipCode(addressDto.getZipCode());
                address.setCountry(addressDto.getCountry());

                addresses.add(address);
            }

            company.setAddresses(addresses);
            companies.add(company);
        }

        user.setCompanies(companies);

//            Set<Tag> newUserTags = new HashSet<>();
//            for (Tag tag : user.getTags()) {
//                logger.warn("tags-------------------> {}", user.getTags());
//                logger.warn("tagId-------------------> {}", tag.getId());
//                Optional<Tag> foundTag = tagService.findById(tag.getId());
//                logger.warn("foundtag-------------------> {}",foundTag.get());
//                if (foundTag.isPresent()) {
//                    System.out.println("Found tag: " + foundTag.get());
//                    newUserTags.add(foundTag.get());
//                } else {
//                    System.out.println("No tag found with ID: " + tag.getId());
//                }
//                //foundTag.ifPresent(newUserTags::add);
//            }
//            user.setTags(newUserTags);

            return userRepository.save(user);
    }
}
