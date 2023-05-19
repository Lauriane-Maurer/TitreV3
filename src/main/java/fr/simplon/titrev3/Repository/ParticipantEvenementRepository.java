package fr.simplon.titrev3.Repository;

import fr.simplon.titrev3.Model.Participant;
import fr.simplon.titrev3.Model.ParticipantEvenement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantEvenementRepository extends JpaRepository <ParticipantEvenement, Long> {

    List<ParticipantEvenement> findByParticipantUsername(String username);
    List<ParticipantEvenement> findByParticipantId(Long participantId);
}
