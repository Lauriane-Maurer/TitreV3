package fr.simplon.titrev3.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="organismes")
public class Organisme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String activite;
    private String description;
    private String adresse;
    private String ville;
    private int code_postal;
    private String url;
    private Double latitude;
    private Double longitude;

    @ManyToMany
    @JoinTable(name = "evenement_organisme",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "organisme_id"))
    private List<Evenement> evenements;

    public Organisme() {
    }

    public Organisme(String libelle, String activite, String description, String adresse, String ville, int code_postal, String url, Double latitude, Double longitude, List<Evenement> evenements) {
        this.libelle = libelle;
        this.activite = activite;
        this.description = description;
        this.adresse = adresse;
        this.ville = ville;
        this.code_postal = code_postal;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.evenements = evenements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
