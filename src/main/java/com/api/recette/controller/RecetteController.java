package com.api.recette.controller;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.recette.entity.Categorie;
import com.api.recette.entity.Etape;
import com.api.recette.entity.Ingredient;
import com.api.recette.entity.Recette;
import com.api.recette.entity.User;
import com.api.recette.service.CategorieService;
import com.api.recette.service.RecetteService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class RecetteController {

    @Autowired
    RecetteService recetteService;

    @Autowired
    CategorieService categorieService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User userAuthed = (User) session.getAttribute("userAuthed"); 
        model.addAttribute("listRecette", recetteService.getAll());
        model.addAttribute("listCategorie", categorieService.getAll());
        if(userAuthed != null){
            List<Recette> listEnregistrement = recetteService.findEnregistrementByUser(userAuthed.getIdUser().intValue());
            model.addAttribute("listEnregistrement", listEnregistrement);
            System.out.println("not  NUUUUUUUUULLLLLLLLLLLLLLL");
            model.addAttribute("user", userAuthed);
            
        }else{
            model.addAttribute("listEnregistrement", new ArrayList());
            System.out.println("NUUUUUUUUULLLLLLLLLLLLLLL");
        }

        return "home";
    } 

    @GetMapping("user/recette/form")
    public String recetteForm(String param) {
        return "recette_form";
    }

    @GetMapping("user/recette/form/image")
    public String imageForm(String param,RedirectAttributes redirectAttributes, HttpSession session) {
        Recette recette = (Recette) session.getAttribute("recette");
        if(recette.getEtapes().size()==0){
            redirectAttributes.addFlashAttribute("error","Veuillez rajoutez des étapes");
            return "redirect:/user/recette/form/etapes";
        }
        return "recette_image_form";
    }

    @GetMapping("user/recette/form/nom")
    public String nomRecette(HttpSession session,String nom,String duree,Integer nombre,String description,
                                RedirectAttributes redirectAttributes) {
        Recette newrecette = new Recette();
        if(nom == "" || duree == "" || nombre == null || description == ""){
            redirectAttributes.addFlashAttribute("error","Veuillez remplir tous les champs");
            return "redirect:/user/recette/form";
        }
        Time time_duree = Time.valueOf(duree);
        Recette recette = (Recette) session.getAttribute("recette");
        User user = (User) session.getAttribute("userAuthed");
        List<Ingredient> ingredients = new ArrayList <Ingredient>();
        List<Etape> etapes = new ArrayList <Etape>();
        List<Categorie> categories = new ArrayList <Categorie>();
        if(recette == null){
            newrecette.setNomRecette(nom);
            newrecette.setDescriptions(description);
            newrecette.setUser(user);
            newrecette.setDuree(time_duree);
            newrecette.setIngredients(ingredients);
            newrecette.setEtapes(etapes);
            newrecette.setCategories(categories);
            newrecette.setNbPersonne(nombre.intValue());
            session.setAttribute("recette", newrecette);
        }else{
            recette.setNomRecette(nom);
            recette.setDescriptions(description);
            recette.setUser(user);
            recette.setDuree(time_duree);
            recette.setNbPersonne(nombre.intValue());
            session.setAttribute("recette", recette);
        }
        return "redirect:/user/recette/form/categories";
    }

    @PostMapping("user/recette/form/insert")
    public String recetteInsert(HttpSession session, @RequestParam(name = "image") MultipartFile image,
                                RedirectAttributes redirectAttributes) {
        try {
            Recette recette = (Recette) session.getAttribute("recette");
            recetteService.ajoutRecette(recette, image);
            session.removeAttribute("recette");   
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/user/recette/form/image";
        }
        return "redirect:/";
    }


    @GetMapping("user/recette/enregistrement")
    public String enregistrement(Model model,RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("userAuthed");
        if(user == null){
            return "redirect:/user/login/form";
        }
        List<Recette> listRecette = recetteService.findEnregistrementByUser(((Number)user.getIdUser()).intValue());
        List<Recette> listEnregistrement = recetteService.findEnregistrementByUser(user.getIdUser().intValue());
        model.addAttribute("listEnregistrement", listEnregistrement);
        model.addAttribute("listRecette", listRecette);
        return "user_enregistrement";
    }

    @ResponseBody
    @GetMapping("user/recette/{idRecette}/enregistrer")
    public String enregistrer(HttpSession session,@PathVariable(name = "idRecette") int idRecette ){
        User user = (User) session.getAttribute("userAuthed");
        if(user == null){
            return "redirect";
        }
        return recetteService.enregistrer(user.getIdUser().intValue(), idRecette);
    }

    @GetMapping("user/recette/mesrecette")
    public String mesRecettes(Model model,RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("userAuthed");
        if(user == null){
            return "redirect:/user/login/form";
        }
        List<Recette> listRecette = recetteService.findByIdUser(user.getIdUser());
        model.addAttribute("listRecette", listRecette);
        return "user_recette";
    }

    @GetMapping("user/recette/{idRecette}/details")
    public String recetteDetails(Model model,RedirectAttributes redirectAttributes, HttpSession session,
                                    @PathVariable(name = "idRecette") Long idRecette) {
        User user = (User) session.getAttribute("userAuthed");             
        Recette recette = recetteService.findByIdRecette(idRecette);
        if(user != null){
            recetteService.insertVue(user.getIdUser().intValue(), idRecette.intValue());
            model.addAttribute("user", user);
        }
        model.addAttribute("recette", recette);
        return "recette_details";
    }

    @ResponseBody
    @GetMapping("user/recette/search")
    public List<List<Recette>> recetteSearch(Model model,RedirectAttributes redirectAttributes, HttpSession session,
                                    @RequestParam(name = "idCategorie", required = false) Integer idCategorie,
                                    @RequestParam(name = "search", required = false) String search) {
        User user = (User) session.getAttribute("userAuthed"); 
        List<List<Recette>> list = new ArrayList<>();
        list.add(recetteService.searchRecette(search, idCategorie));
        List<Recette> listEnregistrement = new ArrayList<>();
        if(user != null){
            listEnregistrement = recetteService.findEnregistrementByUser(user.getIdUser().intValue());
        }
        list.add(listEnregistrement);
        return list;
    }




}
