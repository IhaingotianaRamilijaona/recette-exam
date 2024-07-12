package com.api.recette.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.recette.entity.Ingredient;
import com.api.recette.entity.Recette;
import com.api.recette.entity.Etape;
import com.api.recette.service.EtapeService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EtapeController {

    @Autowired
    EtapeService etapeService;

    @GetMapping("user/recette/form/etapes")
    public String recetteForm(String param,RedirectAttributes redirectAttributes, HttpSession session) {
        Recette recette = (Recette) session.getAttribute("recette");
        if(recette.getIngredients().size()==0){
            redirectAttributes.addFlashAttribute("error","Veuillez rajoutez des ingrédients");
            return "redirect:/user/recette/form/ingredient";
        }
        return "recette_etapes_form";
    }
    
    @ResponseBody
    @PostMapping("user/recette/form/etapes/put")
    public List<Etape> etapesFormPut(HttpSession session,String description) {
        Recette recette = (Recette) session.getAttribute("recette");
        Etape etape = new Etape();
        etape.setNomEtape(description);
        recette.getEtapes().add(etape);
        session.setAttribute("recette", recette);
        return recette.getEtapes();
    }

    @ResponseBody
    @GetMapping("user/recette/form/etapes/delete/{index}")
    public List<Etape> etapesFormDelete(HttpSession session,@PathVariable("index") int index) {
        Recette recette = (Recette) session.getAttribute("recette");
        recette.getEtapes().remove(index);
        session.setAttribute("recette", recette);
        return recette.getEtapes();
    }
}
