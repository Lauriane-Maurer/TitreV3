package fr.simplon.titrev3.Repository;


import fr.simplon.titrev3.Model.Evenement;
import fr.simplon.titrev3.Model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByUsername(String username);


}