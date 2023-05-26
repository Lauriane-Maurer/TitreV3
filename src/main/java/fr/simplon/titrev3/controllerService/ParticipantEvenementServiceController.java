package fr.simplon.titrev3.controllerService;

import fr.simplon.titrev3.Model.Evenement;
import fr.simplon.titrev3.Model.ParticipantEvenement;
import fr.simplon.titrev3.Repository.EvenementRepository;
import fr.simplon.titrev3.Repository.ParticipantEvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ParticipantEvenementServiceController {

    @Autowired
    private ParticipantEvenementRepository repo;


    @Autowired
    public ParticipantEvenementServiceController(ParticipantEvenementRepository fr) {

        this.repo = fr;
    }

    @GetMapping(path = "/rest/participantEvenement")
    public List<ParticipantEvenement> getParticipantEvenement() {
        return repo.findAll();
    }

    @GetMapping(path = "/rest/participantEvenementId/{id}")
    public ParticipantEvenement getParticipantEventDetails(@PathVariable Long id)throws NoSuchElementException {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping(path="/rest/participantEvenement")
    public ParticipantEvenement addParticipantEvent(@RequestBody ParticipantEvenement newParticipantEvent) {
        return repo.save(newParticipantEvent);
    }


    @GetMapping(path= "/rest/participantEvenement/{participantId}")
    public List<ParticipantEvenement> getEvenementsByParticipantId(@PathVariable Long participantId) {
        return repo.findByParticipantId(participantId);
    }

    @GetMapping(path= "/rest/InscritsEvenement/{evenementId}")
    public List<ParticipantEvenement> getParticipantsByEventId(@PathVariable Long evenementId) {
        return repo.findByEvenementId(evenementId);
    }

    @DeleteMapping("/rest/participantEvenement/{id}")
    public void deleteParticipantEvent(@PathVariable Long id) {
        repo.deleteById(id);
    }


    @GetMapping("/rest/participantEvenement/checkRegistration")
    public boolean checkParticipantRegistration(@RequestParam Long participantId, @RequestParam Long evenementId) {
        return repo.existsByParticipantIdAndEvenementId(participantId, evenementId);
    }

}
