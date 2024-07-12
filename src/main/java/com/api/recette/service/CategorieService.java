package com.api.recette.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.recette.entity.Categorie;
import com.api.recette.repository.CategorieRepository;

@Service
public class CategorieService {
    @Autowired
    CategorieRepository categorieRepository;

    public List<Categorie> getAll(){
        return categorieRepository.getAll();
    }

    public Categorie findByIdCategorie(Long idCategorie){
        return categorieRepository.findByIdCategorie(idCategorie);
    }
    
}
