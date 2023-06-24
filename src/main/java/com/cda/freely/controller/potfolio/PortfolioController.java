package com.cda.freely.controller.potfolio;

import com.cda.freely.entity.User;
import com.cda.freely.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
         this.portfolioService = portfolioService;
    }

    @GetMapping("/{portfolioLink}")
    public ResponseEntity<User> getUserByPortfolioLink(@PathVariable String portfolioLink) {
        User user = portfolioService.getUserByPortfolioLink(portfolioLink);
        return ResponseEntity.ok(user);
    }


}
