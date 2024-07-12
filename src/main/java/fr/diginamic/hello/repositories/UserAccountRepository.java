package fr.diginamic.hello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entities.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    
    public UserAccount findByUsername(String username);
}
