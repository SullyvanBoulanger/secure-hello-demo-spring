package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.entities.UserAccount;
import fr.diginamic.hello.repositories.UserAccountRepository;

@Service
public class UserAccountService {
    @Autowired
    public UserAccountRepository userAccountRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void create(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }
}
