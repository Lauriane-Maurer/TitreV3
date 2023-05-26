package fr.simplon.titrev3.ControllerClient;

import fr.simplon.titrev3.Model.Evenement;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Controller
public class EvenementClientController {

    private RestTemplate restTemplate;

    @GetMapping("/admin/listeEvenements")
    public String afficherListeEvenementAdmin(Model model){
        this.restTemplate = new RestTemplate();
        String url ="http://localhost:8083/rest/evenements";
        ResponseEntity<List<Evenement>> response=restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Evenement>>(){});

        List<Evenement> evenements = response.getBody();

        model.addAttribute("evenements", evenements);
        return "listeEvenementsAdmin";
    }


    @GetMapping("/programmation")
    public String getAllEvents(Model model) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/evenements";
        ResponseEntity<List<Evenement>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Evenement>>() {
                });
        List<Evenement> evenements = response.getBody();
        model.addAttribute("evenements", evenements);
        return "programmation";
    }

    @GetMapping("/api/evenements")
    @ResponseBody
    public List<Evenement> getAllEventsAPI() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<List<Evenement>> response = restTemplate.exchange("http://localhost:8083/rest/api/evenements", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Evenement>>() {
        });
        return response.getBody();
    }

    @GetMapping("/evenements/{id}")
    public String getEvent(Model model, @PathVariable Long id) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/evenements/{id}";
        ResponseEntity<Evenement> response = restTemplate.getForEntity(url, Evenement.class, id);
        Evenement evenement = response.getBody();
        model.addAttribute("evenement", evenement);
        return "fiche_evenement";
    }

    @GetMapping("/creationEvenement")
    public String displayEventForm(Model model) {
        Evenement evenement = new Evenement();
        model.addAttribute("evenement", evenement);
        return "formulaireEvenement";
    }

    @PostMapping("/creationEvenement")
    public String addEvent(@ModelAttribute("evenement") Evenement evenement) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/evenements";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Evenement> request = new HttpEntity<>(evenement, headers);
        ResponseEntity<Evenement> response = restTemplate.postForEntity(url, request, Evenement.class);
        return "redirect:/programmation";
    }


    @GetMapping("/UpdateEvent/{id}")
    public String displayFormModEvent(Model model, @PathVariable Long id){
        this.restTemplate = new RestTemplate();
        String url="http://localhost:8083/rest/evenements/{id}";
        ResponseEntity<Evenement> response = restTemplate.getForEntity(url, Evenement.class, id);
        Evenement evenement = response.getBody();
        model.addAttribute("evenement", evenement);
        return "formulaireModifEvenement";
    }


    @PostMapping("UpdateEvent/{id}")
    public String updateEvent (@ModelAttribute("evenement")Evenement evenement, @PathVariable Long id) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/UpdateEvent/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Evenement> request = new HttpEntity<>(evenement, headers);
        ResponseEntity<Evenement> response = restTemplate.exchange(url, HttpMethod.POST, request, Evenement.class, id);
        return "redirect:/admin/gestionnaireAdmin";
    }

    @GetMapping("/getEventPlacesRestantes/{eventId}")
    public String redirectToUpdateEventPlacesRestantes(Model model, @PathVariable Long eventId) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/evenements/{id}";
        ResponseEntity<Evenement> response = restTemplate.getForEntity(url, Evenement.class, eventId);
        Evenement evenement = response.getBody();
        model.addAttribute("evenement", evenement);
        return "redirect:/UpdateEventPlacesRestantes/{eventId}";
    }

    @PostMapping("UpdateEventPlacesRestantes/{eventId}")
    public String updateEventPlacesRestantes(@ModelAttribute("evenement") Evenement evenement, @PathVariable Long eventId) {
        this.restTemplate = new RestTemplate();
        String incrementationPlacesRestantesUrl = "http://localhost:8083/rest/IncrementerPlacesRestantes/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Evenement> request = new HttpEntity<>(evenement, headers);
        ResponseEntity<Evenement> response = restTemplate.exchange(incrementationPlacesRestantesUrl, HttpMethod.POST, request, Evenement.class, eventId);

        return "redirect:/";
    }



    @GetMapping ("evenements/delete/{id}")
    public String delEvent(Model model, @PathVariable Long id){
        this.restTemplate = new RestTemplate();
        String url="http://localhost:8083/rest/evenements/{id}";
        restTemplate.delete(url, id);
        return "redirect:/admin/gestionnaireAdmin";
    }
}