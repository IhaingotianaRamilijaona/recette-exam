package com.api.recette.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public class RecetteRepositoryImpl implements RecetteRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Object> searchRecette(String sql) {
        Query query = entityManager.createNativeQuery(sql +" group by idRecette ORDER BY idRecette ASC ");
        System.out.println("Reallllll SQLLLLL "+sql+" group by idRecette ORDER BY idRecette ASC ");
        return query.getResultList();
    }

}
