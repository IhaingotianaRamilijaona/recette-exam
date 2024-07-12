package com.api.recette.controller;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.recette.entity.User;
import com.api.recette.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/login/form")
    public String loginForm() {
        return "login_form";
    } 
    
    @GetMapping("user/login")
    public String userLogin(String email,String mdp,HttpSession session,
                                RedirectAttributes redirectAttributes){
        try {
            User user = userService.findByEmailAndMdp(email,mdp);
            session.setAttribute("userAuthed", user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/user/login/form";
        }
        return "redirect:/";
    }

    @GetMapping("user/inscription/form")
    public String inscriptionForm(Model model) {
        return "inscription_form";
    }
    
    @GetMapping("user/inscription")
    public String inscription(String email,String mdp,String nom,Model model,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            
            User userInscris = userService.inscription(email,mdp,nom);
            session.setAttribute("userAuthed", userInscris);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/inscription/form";
        }
        return "redirect:/";
    }

    @GetMapping("user/deconnect")
    public String deconnect(HttpSession session) {
        session.removeAttribute("userAuthed");
        return "redirect:/";
    }
    
}
