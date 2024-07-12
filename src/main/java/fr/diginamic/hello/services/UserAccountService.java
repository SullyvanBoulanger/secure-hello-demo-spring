package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.repositories.UserAccountRepository;

@Service
public class UserAccountService {
    @Autowired
    public UserAccountRepository userAccountRepository;

    
}
