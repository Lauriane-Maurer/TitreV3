package fr.simplon.titrev3.controllerService;

import fr.simplon.titrev3.Model.Participant;
import fr.simplon.titrev3.Repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ParticipantServiceController {

    @Autowired
    private ParticipantRepository repo;


    @GetMapping(path = "/rest/participants")
    public List<Participant> getAllParticipants() {

        return repo.findAll();
    }


    @GetMapping(path = "/rest/participantById/{id}")
    public Participant getparticipantsDetails(@PathVariable Long id)throws NoSuchElementException {
        return repo.findById(id).orElseThrow();
    }


    @PostMapping(path="/rest/participants")
    public Participant addParticipant(@RequestBody Participant newParticipant) {

        return repo.save(newParticipant);
    }

    @PostMapping("/rest/participants/{username}")
    public Participant updateParticipant(@RequestBody Participant newParticipant, @PathVariable String username) {
        Participant participant = repo.findByUsername(username);
            participant.setPrenom(newParticipant.getPrenom());
            participant.setNom(newParticipant.getNom());
            participant.setTelephone(newParticipant.getTelephone());
            participant.setEmail(newParticipant.getEmail());
            participant.setCode_postal(newParticipant.getCode_postal());
            return repo.save(participant);
    }


    @DeleteMapping("/rest/participants/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        repo.deleteById(id);
    }


    @GetMapping(path = "/rest/participants/{username}")
    public Participant afficherparticipantparusername(@PathVariable String username) {
        return repo.findByUsername(username);
    }

}
