package fr.simplon.titrev3.ControllerClient;

import fr.simplon.titrev3.Model.Organisme;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class OrganismeClientController {


    private RestTemplate restTemplate;

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
}

