package fr.simplon.titrev3.Repository;

import fr.simplon.titrev3.Model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}
