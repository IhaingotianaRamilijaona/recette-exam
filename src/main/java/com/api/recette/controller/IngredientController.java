package com.api.recette.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.recette.entity.Etape;
import com.api.recette.entity.Ingredient;
import com.api.recette.entity.Recette;
import com.api.recette.service.IngredientService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    

    @GetMapping("user/recette/form/ingredient")
    public String ingredientForm(HttpSession session,RedirectAttributes redirectAttributes) {
        Recette recette = (Recette) session.getAttribute("recette");
        if(recette.getCategories().size()==0){
            redirectAttributes.addFlashAttribute("error","Veuillez rajoutez des catégorie");
            return "redirect:/user/recette/form/categories";
        }
        return "recette_ingredient_form";
    }

    @ResponseBody
    @PostMapping("user/recette/form/ingredient/put")
    public List<Ingredient> ingredientFormPut(HttpSession session,String nom,int quantite,String unite) {
        Recette recette = (Recette) session.getAttribute("recette");
        Ingredient ingredient = new Ingredient();
        ingredient.setNomIngredient(nom);
        ingredient.setQuantite(quantite);
        ingredient.setUnite(unite);
        recette.getIngredients().add(ingredient);
        session.setAttribute("recette", recette);
        return recette.getIngredients();
    }

    @ResponseBody
    @GetMapping("user/recette/form/ingredient/delete/{index}")
    public List<Ingredient> ingredientFormDelete(HttpSession session,@PathVariable("index") int index) {
        Recette recette = (Recette) session.getAttribute("recette");
        recette.getIngredients().remove(index);
        session.setAttribute("recette", recette);
        return recette.getIngredients();
    }

}
