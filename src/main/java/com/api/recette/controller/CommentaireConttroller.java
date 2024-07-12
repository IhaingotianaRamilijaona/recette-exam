package com.api.recette.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.recette.entity.Etape;
import com.api.recette.entity.Commentaire;
import com.api.recette.entity.Recette;
import com.api.recette.entity.User;
import com.api.recette.service.CommentaireService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommentaireConttroller {
    @Autowired
    CommentaireService commentaireService;
    

    @GetMapping("/user/recette/commentaire/insert")
    public String insertCommentaire(Model model,HttpSession session,int idRecette,String commentaire) {
        User user = (User) session.getAttribute("userAuthed");
        commentaireService.insert(commentaire, idRecette, user);
        return "redirect:/user/recette/"+idRecette+"/commentaires";
    }

    @GetMapping("/user/recette/{idRecette}/commentaires")
    public String commentaireForm(Model model,HttpSession session,@PathVariable(name = "idRecette") int idRecette) {
        User user = (User)  session.getAttribute("userAuthed");
        List<Commentaire> listCommentaires = commentaireService.getByIdRecette(idRecette);
        model.addAttribute("listCommentaires", listCommentaires);
        model.addAttribute("idRecette", idRecette);
        if ( session.getAttribute("userAuthed") != null) {
            model.addAttribute("user", user);
        }
        return "recette_commentaires";
    }

}