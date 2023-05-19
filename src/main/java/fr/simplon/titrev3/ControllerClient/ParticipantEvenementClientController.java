package fr.simplon.titrev3.ControllerClient;

import fr.simplon.titrev3.Model.Evenement;
import fr.simplon.titrev3.Model.Participant;
import fr.simplon.titrev3.Model.ParticipantEvenement;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ParticipantEvenementClientController {

    private RestTemplate restTemplate;

    @GetMapping("/InscriptionParticipant/{eventId}/{username}")
    public String afficherInscriptionEvenementForm(Model model, @PathVariable Long eventId, @PathVariable String username) {
        ParticipantEvenement participantEvenement = new ParticipantEvenement();
        model.addAttribute("participantEvenement", participantEvenement);
        this.restTemplate = new RestTemplate();
        String eventUrl = "http://localhost:8083/rest/evenements/{id}";
        ResponseEntity<Evenement> eventResponse = restTemplate.getForEntity(eventUrl, Evenement.class, eventId);
        Evenement evenement = eventResponse.getBody();
        model.addAttribute("evenement", evenement);
        participantEvenement.setEvenement(evenement);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();
        String url = "http://localhost:8083/rest/participants/{username}";
        ResponseEntity<Participant> response = restTemplate.getForEntity(url, Participant.class, username);
        Participant participant = response.getBody();
        if (participant != null) {
            model.addAttribute("participant", participant);
            participantEvenement.setParticipant(participant);
            return "formulaireInscriptionEvenement";
        }else{
            return "redirect:/InfoParticipant/{username}";
        }
    }

    @PostMapping("/InscriptionParticipant")
    public String addParticipantEvent(@ModelAttribute("participantEvenement") ParticipantEvenement participantEvenement) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/participantEvenement";
        Participant participant = participantEvenement.getParticipant();
        Evenement evenement = participantEvenement.getEvenement();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ParticipantEvenement> request = new HttpEntity<>(participantEvenement, headers);
        ResponseEntity<ParticipantEvenement> response = restTemplate.postForEntity(url, request, ParticipantEvenement.class);
        return "redirect:/programmation";
    }


    @GetMapping("/InscriptionsParticipant/{username}")
    public String afficherEvenementsParticipant(Model model, @PathVariable String username) {
        this.restTemplate = new RestTemplate();
        String participantUrl = "http://localhost:8083/rest/participants/{username}";
        ResponseEntity<Participant> response1 = restTemplate.getForEntity(participantUrl, Participant.class, username);
        Participant participant = response1.getBody();
        model.addAttribute("participant", participant);
        if (participant != null) {
            String url = "http://localhost:8083/rest/participantEvenement/{participantId}";
            ResponseEntity<List<ParticipantEvenement>> response2 = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ParticipantEvenement>>() {},
                    participant.getId()
            );
            List<ParticipantEvenement> participantEvenements = response2.getBody();
            model.addAttribute("participantEvenements", participantEvenements);
            return "listeEvenementsParticipant";
        } else {
            return "/programmation";
        }
    }


    @GetMapping("InscriptionsEvenement/{evenementId}")
    public String afficherParticipantsEvenement(Model model, @PathVariable Long evenementId) {
        this.restTemplate = new RestTemplate();

            String url = "http://localhost:8083/rest/InscritsEvenement/{eventId}";
            ResponseEntity<List<ParticipantEvenement>> response2 = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ParticipantEvenement>>() {},
                    evenementId
            );
            List<ParticipantEvenement> participantEvenements = response2.getBody();
            model.addAttribute("participantEvenements", participantEvenements);
            return "listeInscritsEvenement";

    }
}
