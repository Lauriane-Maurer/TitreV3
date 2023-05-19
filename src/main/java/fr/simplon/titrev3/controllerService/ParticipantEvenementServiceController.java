package fr.simplon.titrev3.controllerService;

import fr.simplon.titrev3.Model.Evenement;
import fr.simplon.titrev3.Model.ParticipantEvenement;
import fr.simplon.titrev3.Repository.EvenementRepository;
import fr.simplon.titrev3.Repository.ParticipantEvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
