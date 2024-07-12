package com.api.recette.entity;

import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "recette")
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecette")
    private Long idRecette;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;

    @Column(name = "nomRecette")
    private String nomRecette;

    @Column(name = "personne")
    private int nbPersonne;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "images")
    private String image;

    @Column(name = "duree")
    private Time duree;
    
    @JsonIgnore
    @OneToMany(mappedBy = "recette")
    private List<Etape> etapes;
    
    @JsonIgnore
    @OneToMany(mappedBy = "recette")
    private List<Ingredient> ingredients;
    
    @JsonIgnore
    @OneToMany(mappedBy = "recette")
    private List<Commentaire> commentaires;

    @OneToMany
    @JoinTable(
        name = "recetteCategorie",
        joinColumns = @JoinColumn(name = "idRecette"),
        inverseJoinColumns = @JoinColumn(name = "idCategorie")
    )
    private List<Categorie> categories;

    public Long getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(Long idRecette) {
        this.idRecette = idRecette;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNomRecette() {
        return nomRecette;
    }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public int getNbPersonne() {
        return nbPersonne;
    }

    public void setNbPersonne(int nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public List<Etape> getEtapes() {
        return etapes;
    }

    public void setEtapes(List<Etape> etapes) {
        this.etapes = etapes;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
    

}
