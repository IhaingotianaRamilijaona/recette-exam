package com.api.recette.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "commentaire")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCommentaires")
    private Long idCommentaires;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRecette")
    private Recette recette;

    @Column(name = "commentaire")
    private String commentaire;

    public Long getIdCommentaires() {
        return idCommentaires;
    }

    public void setIdCommentaires(Long idCommentaires) {
        this.idCommentaires = idCommentaires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
