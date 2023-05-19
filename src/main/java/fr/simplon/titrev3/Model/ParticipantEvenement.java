package fr.simplon.titrev3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="participant_evenement")
public class ParticipantEvenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_participant", referencedColumnName = "id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "id_evenement", referencedColumnName = "id")
    private Evenement evenement;

    public ParticipantEvenement() {
    }

    public ParticipantEvenement(Participant participant, Evenement evenement) {
        this.participant = participant;
        this.evenement = evenement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
}
