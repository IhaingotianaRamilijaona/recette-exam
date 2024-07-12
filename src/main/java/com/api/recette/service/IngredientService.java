package com.api.recette.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.recette.repository.IngredientRepository;

@Service
public class IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;
    
}
