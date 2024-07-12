package com.api.recette.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "etapes")
public class Etape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEtape")
    private Long idEtape;

    @Column(name = "nomEtape")
    private String nomEtape;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRecette")
    private Recette recette;

    public Long getIdEtape() {
        return idEtape;
    }

    public void setIdEtape(Long idEtape) {
        this.idEtape = idEtape;
    }

    public String getNomEtape() {
        return nomEtape;
    }

    public void setNomEtape(String nomEtape) {
        this.nomEtape = nomEtape;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    
}
