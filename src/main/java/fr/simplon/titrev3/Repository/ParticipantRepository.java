package fr.simplon.titrev3.Repository;

import fr.simplon.titrev3.Model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByUsername(String username);

}