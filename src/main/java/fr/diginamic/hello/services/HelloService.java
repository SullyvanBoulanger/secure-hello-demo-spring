package fr.diginamic.hello.services;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    
    public String sayWelcome(){
        return "Je suis la classe de service et je vous dis Bonjour";
    }
}
