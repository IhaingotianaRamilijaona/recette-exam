package com.api.recette.repository;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.recette.entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    @Query("SELECT c FROM Categorie c")
    List<Categorie> getAll();

    Categorie findByIdCategorie(Long idCategorie);

    @Modifying
    @Query(value="INSERT INTO recettecategorie VALUES (:idRecette, :idCategorie)",nativeQuery = true)
    void insertCategorieRecette(int idRecette, int idCategorie);
}
