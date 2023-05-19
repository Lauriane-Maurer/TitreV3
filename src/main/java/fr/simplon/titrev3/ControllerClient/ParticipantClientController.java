package fr.simplon.titrev3.ControllerClient;


import fr.simplon.titrev3.Model.Evenement;
import fr.simplon.titrev3.Model.Participant;
import fr.simplon.titrev3.Model.ParticipantEvenement;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Controller
public class ParticipantClientController {

    private RestTemplate restTemplate;

    @GetMapping("/creationInfoPerso")
    public String afficherInfoPersoForm(Model model) {
        Participant participant = new Participant();
        model.addAttribute("participant", participant);
        return "formulaireInfoPerso";
    }


    @GetMapping("/InfoParticipant/{username}")
    public String afficherFormulaireParticipant(Model model, @PathVariable String username) {
        this.restTemplate = new RestTemplate();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();
        Participant participant = new Participant();
        participant.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("participant", participant);
        return "formulaireInfoParticipant";
    }

    @PostMapping("/InfoParticipant")
    public String enregistrerParticipant(@ModelAttribute("participant") Participant participant) {
        this.restTemplate = new RestTemplate();

        // Enregistrer le participant
        String Url = "http://localhost:8083/rest/participants";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Participant> participantRequest = new HttpEntity<>(participant, headers);
        ResponseEntity<Participant> participantResponse = restTemplate.postForEntity(Url, participantRequest, Participant.class);
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


}
