package fr.simplon.titrev3.Repository;

import fr.simplon.titrev3.Model.Participant;
import fr.simplon.titrev3.Model.ParticipantEvenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantEvenementRepository extends JpaRepository <ParticipantEvenement, Long> {


}
