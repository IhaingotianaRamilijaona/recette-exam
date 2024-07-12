package com.api.recette.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.recette.entity.User;
import com.api.recette.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    

    public User findByEmailAndMdp(String email,String mdp) throws Exception{
        if(email == ""){
            throw new Exception("email invalide ");
        }
        if(mdp == ""){
            throw new Exception("Mot de passe invalide");
        }
        User user  = userRepository.findByEmailAndMdp(email,mdp);
        if(user == null){
            throw new Exception("Email ou mot de passe incorrect");
        }
        return user;
    }


    public User inscription(String email,String mdp,String nom) throws Exception {
        if(nom == ""){
            throw new Exception("Nom invalide ");
        }
        else if(email == ""){
            throw new Exception("Email invalide ");
        }
        else if(mdp == ""){
            throw new Exception("Mot de passe invalide");
        }
        User user = new User();
        user.setNomUser(nom);
        user.setEmail(email);
        user.setMdp(mdp);
        User userInscris = userRepository.save(user);
        return userInscris;
    }
}
