package com.cda.freely.controller.user;

import com.cda.freely.entity.Service;
import com.cda.freely.entity.Training;
import com.cda.freely.entity.User;
import com.cda.freely.exception.NotFoundException;
import com.cda.freely.service.ServiceService;
import com.cda.freely.service.UserService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/services")
public class ServiceController {
    private ServiceService serviceService;
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(ServiceController.class);
    @Autowired
    public ServiceController(ServiceService serviceService, UserService userService) {
        this.serviceService = serviceService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @JsonView({Views.Service.class})
    public ResponseEntity<?> getService(@RequestHeader("Authorization") String bearerToken,
                                         @PathVariable Long id){
        logger.warn("bearer token ---------- {}", bearerToken);
        userService.checkUser(bearerToken);
        Service service = serviceService.findServiceById(id).orElseThrow(() -> new NotFoundException("Skill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(service);

    }

    @GetMapping("")
    @JsonView({Views.Service.class})
    public ResponseEntity<?> getServices(@RequestHeader("Authorization") String bearerToken) {
        logger.warn("bearer token ----------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);
        List<Service> services = serviceService.getServices(usernameExists.getId());
        return ResponseEntity.ok(services);
    }
    @PostMapping("")
    @JsonView({Views.Service.class})
    public ResponseEntity<?> addService(@RequestHeader("Authorization") String bearerToken,
                                         @RequestBody Service service){
        logger.warn("bearer token ---------- {}", bearerToken);

        User usernameExists = userService.checkUser(bearerToken);;
        logger.info("Add a new skill-----------: {}",usernameExists);
        service.setUser(usernameExists);
        Service newService = serviceService.saveService(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(newService);

    }

    @PutMapping("/{id}")
    @JsonView({Views.Service.class})
    public ResponseEntity<?> editService(@RequestHeader("Authorization") String bearerToken,
                                       @RequestBody Service updatedService,
                                       @PathVariable Long id){
        logger.warn("bearer token ---------- {}", bearerToken);
        User usernameExists = userService.checkUser(bearerToken);;
        updatedService.setUser(usernameExists);
        Service newService = serviceService.updateService(updatedService, id);

        return ResponseEntity.status(HttpStatus.OK).body(newService);

    }
    @DeleteMapping("")
    @JsonView({Views.Service.class})
    public ResponseEntity<?> deleteService(@RequestHeader("Authorization") String bearerToken,
                                         @RequestBody Long id){
        logger.warn("bearer token ---------- {}", bearerToken);
        userService.checkUser(bearerToken);
        serviceService.deleteServiceById(id);
        return ResponseEntity.ok().build();
    }
}
