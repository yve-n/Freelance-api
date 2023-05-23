package com.cda.freely.service;

import com.cda.freely.entity.Address;
import com.cda.freely.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AddressService {
    private final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private AddressRepository addressRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * find an address
     * @param id
     * @return Address
     */
    public Optional<Address> findAddressById(Long id){
        return addressRepository.findById(id);
    }

    /**
     * save an address
     * @param address
     * @return Address
     */
    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }

    /**
     * delete an address
     * @param id
     */
    public void deleteAddressById(Long id){
        addressRepository.deleteById(id);
    }
}
