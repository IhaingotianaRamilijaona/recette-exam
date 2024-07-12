package com.api.recette.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.recette.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient,Long>  {
    
}
