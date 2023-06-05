package com.cda.freely.service;

import com.cda.freely.entity.Training;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {
    private final Logger logger = LoggerFactory.getLogger(ServiceService.class);
    private ServiceRepository serviceRepository;
    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * find a particular user service
     * @param id service's id
     * @return Service
     */
    public Optional<com.cda.freely.entity.Service> findServiceById(Long id){
        return serviceRepository.findById(id);
    }

    /**
     * store a service in database
     * @param service service data to store
     * @return a new service
     */
    public com.cda.freely.entity.Service saveService(com.cda.freely.entity.Service service){
        return serviceRepository.save(service);
    }

    public com.cda.freely.entity.Service updateService(com.cda.freely.entity.Service updatedService, Long id){
        com.cda.freely.entity.Service service = this.findServiceById(id).orElseThrow(() -> new NotFoundException("Service not found"));

        service.setName(updatedService.getName());
        service.setPrice(updatedService.getPrice());
        service.setUser(updatedService.getUser());
        return this.saveService(service);
    }

    /**
     * delete a service
     * @param id identifier of the service
     */
    public void deleteServiceById(Long id){
        if (!serviceRepository.existsById(id)) {
            throw new NotFoundException("Training not found");
        }
        serviceRepository.deleteById(id);
    }
    public List<com.cda.freely.entity.Service> getServices(Long id){
        return serviceRepository.findAllByUserId(id);
    }

}
