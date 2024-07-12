package com.api.recette.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.recette.entity.Commentaire;
import com.api.recette.entity.User;
import com.api.recette.repository.CommentaireRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentaireService {
    @Autowired
    CommentaireRepository commentaireRepository;
    
    @Autowired
    RecetteService recetteService;

    @Transactional
    public void insert(String commentaire,int idRecette, User user){
        Commentaire newCommentaire = new Commentaire();
        newCommentaire.setCommentaire(commentaire);
        newCommentaire.setUser(user);
        newCommentaire.setRecette(recetteService.findByIdRecette(((Number) idRecette).longValue()));
        commentaireRepository.save(newCommentaire);
    }

    public List<Commentaire> getByIdRecette(int idRecette){
        return commentaireRepository.getByIdRecette(idRecette);
    }
}
