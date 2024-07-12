package com.api.recette.repository;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.recette.entity.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire,Long>  {

    @Query(value = "SELECT * FROM Commentaire c where c.idRecette = :idRecette",nativeQuery = true)
    List<Commentaire> getByIdRecette(int idRecette);
}
