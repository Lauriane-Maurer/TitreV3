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
public class ParticipantEvenementClientController {

    private RestTemplate restTemplate;

    @GetMapping("/InscriptionParticipant/{eventId}/{username}")
    public String afficherFormulaireInscriptionParticipant(Model model, @PathVariable Long eventId, @PathVariable String username) {
        ParticipantEvenement participantEvenement = new ParticipantEvenement();
        model.addAttribute("participantEvenement", participantEvenement);
        this.restTemplate = new RestTemplate();
        String eventUrl = "http://localhost:8083/rest/evenements/{id}";
        ResponseEntity<Evenement> eventResponse = restTemplate.getForEntity(eventUrl, Evenement.class, eventId);
        Evenement evenement = eventResponse.getBody();

        if ((evenement.getPlaces_restantes() != null) && (evenement.getPlaces_restantes() != 0) ) {
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
            } else {
                return "redirect:/InfoParticipant/{username}";
            }
        }else {
            model.addAttribute("confirmationMessage", "Cet évènement est déjà complet.");
            return "confirmation";
        }
    }

    @PostMapping("/InscriptionParticipant")
    public String inscriptionParticipantEvenement(@ModelAttribute("participantEvenement") ParticipantEvenement participantEvenement, Model model) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/participantEvenement";
        Participant participant = participantEvenement.getParticipant();
        Evenement evenement = participantEvenement.getEvenement();

        Long participantId = participant.getId();
        Long evenementId = evenement.getId();
        String checkRegistrationUrl = "http://localhost:8083/rest/participantEvenement/checkRegistration?participantId=" + participantId + "&evenementId=" + evenementId;
        ResponseEntity<Boolean> responseCheck = restTemplate.getForEntity(checkRegistrationUrl, Boolean.class);
        if (responseCheck.getBody()) {
            model.addAttribute("confirmationMessage", "Vous êtes déjà inscrit à cet évènement.");
            return "confirmation";
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ParticipantEvenement> request = new HttpEntity<>(participantEvenement, headers);
            ResponseEntity<ParticipantEvenement> response = restTemplate.postForEntity(url, request, ParticipantEvenement.class);

            String decrementationPlacesRestantesUrl = "http://localhost:8083/rest/DecremeneterPlaces/{id}";
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Evenement> request2 = new HttpEntity<>(evenement, headers);
            ResponseEntity<Evenement> response2 = restTemplate.exchange(decrementationPlacesRestantesUrl, HttpMethod.POST, request, Evenement.class, evenementId);

            model.addAttribute("confirmationMessage", "Votre inscription a bien été enregistrée. Retrouvez la liste des évènements auxquels vous êtes inscrit.e en cliquant sur 'Mes évènements' dans la barre de navigation.");
            return "confirmation";
        }
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
            String Evenementurl = "http://localhost:8083/rest/evenements/{id}";
            ResponseEntity<Evenement> response1 = restTemplate.exchange(
                Evenementurl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Evenement>() {},
                evenementId
            );
            Evenement evenement = response1.getBody();
            model.addAttribute("evenement", evenement);

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


    @GetMapping("participantEvenement/delete/{id}")
    public String supprimerParticipantEvenement(Model model, @PathVariable Long id){
        this.restTemplate = new RestTemplate();

        String url2="http://localhost:8083/rest/participantEvenement/{id}";
        restTemplate.delete(url2, id);

        return "redirect:/programmation";
    }


}
