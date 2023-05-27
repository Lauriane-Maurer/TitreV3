package fr.simplon.titrev3.ControllerClient;

import fr.simplon.titrev3.Model.Evenement;
import fr.simplon.titrev3.Model.Organisme;
import fr.simplon.titrev3.Model.Participant;
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
public class OrganismeClientController {


    private RestTemplate restTemplate;


    @GetMapping("/admin/listeOrganismes")
    public String displayListOrganizationsAdmin(Model model){
        this.restTemplate = new RestTemplate();
        String url ="http://localhost:8083/rest/organismes";
        ResponseEntity<List<Organisme>> response=restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Organisme>>(){});
        List<Organisme> organismes = response.getBody();
        model.addAttribute("organismes", organismes);
        return "listeOrganismesAdmin";
    }

    @GetMapping("/createOrganization")
    public String displayOrganizationForm(Model model) {
        this.restTemplate = new RestTemplate();
        Organisme organisme = new Organisme();
        model.addAttribute("organisme", organisme);
        return "formulaireOrganisme";
    }

    @PostMapping("/addOrganization")
    public String addOrganization(@ModelAttribute("organisme") Organisme organisme) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/organismes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Organisme> request = new HttpEntity<>(organisme, headers);
        ResponseEntity<Organisme> response = restTemplate.postForEntity(url, request, Organisme.class);
        return "redirect:/territoire";
    }

    @GetMapping("/getOrganization/{id}")
    public String displayFormModOrganization(Model model, @PathVariable Long id){
        this.restTemplate = new RestTemplate();
        String url="http://localhost:8083/rest/organismes/{id}";
        ResponseEntity<Organisme> response = restTemplate.getForEntity(url, Organisme.class, id);
        Organisme organisme = response.getBody();
        model.addAttribute("organisme", organisme);
        return "formulaireModifOrganisme";
    }

    @PostMapping("updateOrganization/{id}")
    public String updateOrganization (@ModelAttribute("organisme")Organisme organisme, @PathVariable Long id) {
        this.restTemplate = new RestTemplate();
        String url = "http://localhost:8083/rest/updateOrganization/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Organisme> request = new HttpEntity<>(organisme, headers);
        ResponseEntity<Organisme> response = restTemplate.exchange(url, HttpMethod.PUT, request, Organisme.class, id);
        return "redirect:/admin/gestionnaireAdmin";
    }

    @GetMapping("/territoire")
    public String territoireclient(Model model){
        this.restTemplate = new RestTemplate();
        String url ="http://localhost:8083/rest/organismes";
        ResponseEntity<List<Organisme>> response=restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Organisme>>(){});

        List<Organisme> organismes = response.getBody();

        model.addAttribute("organismes", organismes);
        return "territoire";
    }

    @GetMapping("/api/organismes")
    @ResponseBody
    public List<Organisme> getAllOrganismesAPIclient() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<List<Organisme>> response = restTemplate.exchange("http://localhost:8083/rest/api/organismes", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Organisme>>() {});
        return response.getBody();
    }

    @GetMapping ("organismes/delete/{id}")
    public String delOrganization(Model model, @PathVariable Long id){
        this.restTemplate = new RestTemplate();
        String url="http://localhost:8083/rest/organismes/{id}";
        restTemplate.delete(url, id);
        return "redirect:/admin/gestionnaireAdmin";
    }


}

