package com.cda.freely.controller.family;

import com.cda.freely.entity.Family;
import com.cda.freely.service.FamilyService;
import com.cda.freely.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/family")
public class FamilyController {
    private FamilyService familyService;
    @Autowired
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("")
    public ResponseEntity<?> getFamily(){
        return ResponseEntity.status(HttpStatus.OK).body("family");
    }
    @GetMapping("/all")
    @JsonView({Views.FamilyWithoutUsers.class})
    public ResponseEntity<?> getFamilies(){
        System.out.println(familyService.getFamilies().size());
        System.out.println(familyService.getFamilies().size());
        System.out.println(familyService.getFamilies().size());
        System.out.println(familyService.getFamilies().size());
        System.out.println(familyService.getFamilies().size());
        System.out.println(familyService.getFamilies().size());
        return ResponseEntity.status(HttpStatus.OK).body(familyService.getFamilies());

    }
}
