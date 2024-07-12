package com.api.recette.repository;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.recette.entity.Recette;

public interface RecetteRepository extends JpaRepository<Recette,Long>, RecetteRepositoryCustom {
    
    @Query("SELECT r FROM Recette r")
    List<Recette> getAll();

    Recette findByIdRecette(Long idRecette);

    @Query(value = "SELECT idRecette FROM enregistrement  where idUser = :idUser",nativeQuery = true)
    List<Object> findEnregistrementByUser(int idUser);

    @Query(value = "SELECT idRecette FROM enregistrement where idUser = :idUser AND idRecette = :idRecette",nativeQuery = true)
    Object findEnregistrement(int idUser, int idRecette);

    @Modifying
    @Query(value = "DELETE FROM enregistrement where idUser = :idUser AND idRecette = :idRecette",nativeQuery = true)
    void deleteEnregistrement(int idUser, int idRecette);

    @Modifying
    @Query(value = "INSERT INTO enregistrement VALUES(:idUser,:idRecette)",nativeQuery = true)
    void insertEnregistrement(int idUser, int idRecette);

    @Query(value = "SELECT * FROM Recette r where idUser  = :idUser",nativeQuery = true)
    List<Recette> findByIdUser(Long idUser);

    @Modifying
    @Query(value = "INSERT INTO vue VALUES(:idUser,:idRecette)",nativeQuery = true)
    void insertVue(int idUser , int idRecette);

    @Query(value = "SELECT idRecette FROM vue where idUser = :idUser AND idRecette = :idRecette",nativeQuery = true)
    Object findVue(int idUser, int idRecette);    
}
