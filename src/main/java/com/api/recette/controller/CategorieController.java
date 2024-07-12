package com.api.recette.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.recette.entity.Ingredient;
import com.api.recette.entity.Recette;
import com.api.recette.entity.Categorie;
import com.api.recette.service.CategorieService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CategorieController {

    @Autowired
    CategorieService categorieService;
    
    @GetMapping("user/recette/form/categories")
    public String categoriesForm(Model model) {
        List<Categorie> listCategories = categorieService.getAll();
        model.addAttribute("listCategories", listCategories);
        return "recette_categorie_form";
    }
    
    @ResponseBody
    @PostMapping("user/recette/form/categories/put")
    public List<Categorie> categoriesFormPut(HttpSession session,Long idCategorie) {
        Recette recette = (Recette) session.getAttribute("recette");
        Categorie categorie = categorieService.findByIdCategorie(idCategorie);
        if(!recette.getCategories().contains(categorie)){
            recette.getCategories().add(categorie);
        }
        session.setAttribute("recette", recette);
        return recette.getCategories();
    }

    @ResponseBody
    @GetMapping("user/recette/form/categories/delete/{index}")
    public List<Categorie> categoriesFormDelete(HttpSession session,@PathVariable("index") int index) {
        Recette recette = (Recette) session.getAttribute("recette");
        recette.getCategories().remove(index);
        session.setAttribute("recette", recette);
        return recette.getCategories();
    }
    
    
    
}
