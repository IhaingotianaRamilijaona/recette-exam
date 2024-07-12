package com.api.recette.service;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.recette.entity.Categorie;
import com.api.recette.entity.Etape;
import com.api.recette.entity.Ingredient;
import com.api.recette.entity.Recette;
import com.api.recette.repository.CategorieRepository;
import com.api.recette.repository.EtapeRepository;
import com.api.recette.repository.IngredientRepository;
import com.api.recette.repository.RecetteRepository;

import jakarta.transaction.Transactional;

@Service
public class RecetteService {
    @Autowired
    RecetteRepository recetteRepository;
    
    @Autowired
    IngredientRepository ingredientRepository;
    
    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    EtapeRepository etapeRepository;

    @Transactional
    public void ajoutRecette(Recette recette , MultipartFile image) throws Exception{
        if(image == null){
            throw new Exception ("Veuillez choisir une image");
        }
        String fileName = image.getOriginalFilename();
        String uploadDirectory = "D:/Dev/recette-cuisine/recette/src/main/resources/static/images";
        File destFile = new File(uploadDirectory + File.separator + fileName);
        image.transferTo(destFile);

        recette.setImage(fileName);
        Recette insertedRecette = recetteRepository.save(recette);

        for (Ingredient ingredient : insertedRecette.getIngredients()) {
            ingredient.setRecette(insertedRecette);
            ingredientRepository.save(ingredient);
        };

        for (Categorie categorie : insertedRecette.getCategories()) {
            categorieRepository.insertCategorieRecette(insertedRecette.getIdRecette().intValue(), categorie.getIdCategorie().intValue());
        }

        for (Etape etape : insertedRecette.getEtapes()) {
            etape.setRecette(insertedRecette);
            etapeRepository.save(etape);
        }
    }

    public List<Recette> getAll(){
        return recetteRepository.getAll();
    }

    public Recette findByIdRecette(Long idRecette){
        return  recetteRepository.findByIdRecette(idRecette);
    }

    public List<Recette> findEnregistrementByUser(int idUser){
        List<Recette> listRecette = new ArrayList<Recette>();
        List<Object> ids = recetteRepository.findEnregistrementByUser(idUser);
        for (Object object : ids) {
            Recette recette = recetteRepository.findByIdRecette(((Number) object).longValue());
            listRecette.add(recette);
        }
        return listRecette;
    }

    @Transactional
    public String enregistrer(int idUser,int idRecette){
        String action = "saved";
        if(recetteRepository.findEnregistrement(idUser, idRecette) == null){
            recetteRepository.insertEnregistrement(idUser, idRecette);
        }else{
            recetteRepository.deleteEnregistrement(idUser, idRecette);
            action = "unsaved";
        }
        return action;
    }

    public List<Recette> findByIdUser(Long idUser){
        return recetteRepository.findByIdUser(idUser);
    }

    @Transactional
    public void insertVue(int idUser , int idRecette){
        // if(recetteRepository.findVue(idUser, idRecette)==null){
        //     recetteRepository.insertVue(idUser, idRecette);
        // }
        recetteRepository.insertVue(idUser, idRecette);
    }


    public List<Object> searchRecetteFirstQuery(String searchBar, Integer idCategorie){
        String sql = "SELECT idRecette FROM recetteView ";
        String[] tables = {"nomRecette", "descriptions", "nomCategorie", "nomIngredient"};
        if(searchBar != null && searchBar != ""){
            for (int i = 0; i < tables.length; i++) {
                if(i==0){
                    sql += " WHERE  ( UPPER(" + tables[i] + ") LIKE UPPER('%" + searchBar + "%') ";
                }
                else if(i == tables.length-1){
                    sql += " OR UPPER(" + tables[i] + ") LIKE UPPER('%" + searchBar + "%')  ) ";
                }
                else{
                    sql += " OR UPPER(" + tables[i] + ") LIKE UPPER('%" + searchBar + "%') ";
                }
            }
            if(idCategorie != null ){
                sql += " AND idCategorie = "+idCategorie;
            }
        }else if(idCategorie != null ){
            sql += " WHERE idCategorie = "+idCategorie;
        }

        System.out.println("idCategorieeeeeeeeeeee"+ idCategorie);
        List<Object> ids = recetteRepository.searchRecette(sql);
        System.out.println("SQLLLLLLLLLLL First"+sql);
        for (Object object : ids) {
            System.out.println("iddddsssss : "+object);
        }
        return ids;
    }

    public List<Object> searchRecetteSecondQuery(String searchBar, Integer idCategorie){ 
        String[] tables = {"nomRecette", "descriptions","nomCategorie", "nomIngredient"};
        List<Object> ids = new ArrayList<Object>();
        String[] subSearch = searchBar.split(" ");
        if(searchBar != null && searchBar != ""){
            for (int i = 0; i < subSearch.length; i++) {
                String sql = "SELECT idRecette FROM recetteView ";
                for (int j = 0; j < tables.length; j++) {
                    if(j==0){
                        sql += " WHERE ( UPPER(" + tables[j] + ") LIKE UPPER('%" + subSearch[i] + "%') ";
                    }
                    else if(j == tables.length-1){
                        sql += " OR UPPER(" + tables[j] + ") LIKE UPPER('%" + subSearch[i] + "%')  ) ";
                    }
                    else{
                        sql += " OR UPPER(" + tables[j] + ") LIKE UPPER('%" + subSearch[i] + "%') ";
                    }
                }
                if(idCategorie != null ){
                    sql += " AND idCategorie = "+idCategorie;
                }
                System.out.println("SQLLLLLLLLLLL second"+sql);
                ids.addAll(recetteRepository.searchRecette(sql));
            }
        }
        return ids;
    }

    public List<Recette> searchRecette(String searchBar, Integer idCategorie){
        List<Recette> listRecette = new ArrayList<Recette>();
        List<Object> ids = new ArrayList<Object>();
        ids.addAll(searchRecetteFirstQuery(searchBar, idCategorie));
        ids.addAll(searchRecetteSecondQuery(searchBar, idCategorie));
        List<Object> filteredids = ids.stream()
                                        .distinct()
                                        .collect(Collectors.toList());
        for (Object object : filteredids) {
            Recette recette = recetteRepository.findByIdRecette(((Number) object).longValue());
            listRecette.add(recette);
        }
        return listRecette;
    }
}
