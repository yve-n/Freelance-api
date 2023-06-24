package com.cda.freely.service.portfolio;

import com.cda.freely.entity.User;
import com.cda.freely.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PortfolioService {

    private UserRepository userRepository;

    @Autowired
    public PortfolioService(UserRepository userRepository) {
          this.userRepository = userRepository;
    }

    public User getUserByPortfolioLink(String portfolioLink) {
        Optional<User> optionalUser = userRepository.findByPortfolioLink(portfolioLink);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
}
