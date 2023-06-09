package fr.simplon.titrev3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
    @Table(name="evenements")
    public class Evenement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotNull
        private String type;
        @NotNull
        private String titre;
        @NotNull
        @Column(length = 1000)
        private String description;
        @Future(message = "La date de début doit être dans le futur")
        @NotNull
        private LocalDateTime dateDebut;
        @Future(message = "La date de fin doit être dans le futur")
        @NotNull
        private LocalDateTime dateFin;

        private boolean limite_places;
        private Integer places_totales;
        private Integer places_restantes;
        private Double tarif;
        private String intervenant;
        private String photo;

        @JsonIgnore
        @OneToMany(mappedBy = "evenement")
        @Cascade(org.hibernate.annotations.CascadeType.DELETE)
        private List<ParticipantEvenement> participantEvenements;

        @ManyToMany
        @JoinTable(name = "evenement_organisme",
                joinColumns = @JoinColumn(name = "evenement_id"),
                inverseJoinColumns = @JoinColumn(name = "organisme_id"))
        private List<Organisme> organismes;

    public Evenement() {
    }

    public Evenement(String type, String titre, String description, LocalDateTime dateDebut, LocalDateTime dateFin, boolean limite_places, Integer places_totales, Integer places_restantes, Double tarif, String intervenant, String photo, List<ParticipantEvenement> participantEvenements, List<Organisme> organismes) {
        this.type = type;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.limite_places = limite_places;
        this.places_totales = places_totales;
        this.places_restantes = places_restantes;
        this.tarif = tarif;
        this.intervenant = intervenant;
        this.photo = photo;
        this.participantEvenements = participantEvenements;
        this.organismes = organismes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isLimite_places() {
        return limite_places;
    }

    public void setLimite_places(boolean limite_places) {
        this.limite_places = limite_places;
    }

    public Integer getPlaces_totales() {
        return places_totales;
    }

    public void setPlaces_totales(Integer places_totales) {
        this.places_totales = places_totales;
    }

    public Integer getPlaces_restantes() {
        return places_restantes;
    }

    public void setPlaces_restantes(Integer places_restantes) {
        this.places_restantes = places_restantes;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public String getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(String intervenant) {
        this.intervenant = intervenant;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<ParticipantEvenement> getParticipantEvenements() {
        return participantEvenements;
    }

    public void setParticipantEvenements(List<ParticipantEvenement> participantEvenements) {
        this.participantEvenements = participantEvenements;
    }

    public List<Organisme> getOrganismes() {
        return organismes;
    }

    public void setOrganismes(List<Organisme> organismes) {
        this.organismes = organismes;
    }

    @PrePersist
    private void initPlacesRestantes() {
        if (places_totales != null) {
            places_restantes = places_totales;
        }
    }

    public void decrementerPlacesRestantes() {
        if (this.places_restantes != null) {
            this.places_restantes--;
        }
    }

    public void incrementerPlacesRestantes() {
        if (this.places_restantes != null) {
            this.places_restantes++;
        }
    }

}
