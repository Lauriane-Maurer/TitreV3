package fr.simplon.titrev3.controllerService;

import fr.simplon.titrev3.Model.Organisme;
import fr.simplon.titrev3.Repository.OrganismeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class OrganismeServiceController {

    private OrganismeRepository repo;


    @Autowired
    public OrganismeServiceController(OrganismeRepository fr) {
        this.repo = fr;

        //this.repo.save(new Organisme("Gaec Gwel Ar Mor", "producteur","7 Bourg", "Lampaul-Ploudalmezeau", 29830, "www.gwelarmor.fr", 48.56241900228485, -4.609485545551342));
        //this.repo.save(new Organisme("Kanevedenn", "agriculture","15 route de Kervizin", "Lampaul-Ploudalmezeau", 29830, "www.maboutiquefermiere.fr", 48.56466226388162, -4.647926511065619));
        //this.repo.save(new Organisme("L'atelier de Viliv", "artisanat","3 rue de la fontaine", "Coat-Méal", 29870, "www.latelierdeviliv.fr", 48.5186046346549, -4.540717612891484));
        //this.repo.save(new Organisme("Du haut de ma dune", "artisanat","130 Ar lud", "Landéda", 29870, "www.duhautdemadune.com", 48.61263611216837, -4.557234647722291));
        //this.repo.save(new Organisme("Théatre à Molette", "animation","6 rue Mézou Kervidré", "Le Conquet", 29217, "www.theatreamolette.fr", 48.35226286118822, -4.762655971098742));
        //.repo.save(new Organisme("Parc Naturel Marin d'Iroise", "environnement","23 rue radio", "Le Conquet", 29217, "www.parc-marin-iroise.fr", 48.35482736547393, -4.779469303808932));
        //this.repo.save(new Organisme("Bergerie des abers", "agriculture","Gorréquéar", "Plouvien", 29860, "www.bergeriedesabers.fr",48.540765697781275, -4.522247283475145));
        //this.repo.save(new Organisme("Ar Vro Bagan", "animation","ZA du Hellez", "Plouguerneau", 29880, "www.arvrobagan.bzh",48.62956014028035, -4.484334853087361));
        //this.repo.save(new Organisme("Brasserie des abers", "artisanat","2 avenue de Portsall", "Ploudalmézeau", 29830, "www.facebook.com/brasseriedesabers/",48.56085564281277, -4.630583807123573));

    }

    @GetMapping(path = "/rest/organismes")
    public List<Organisme> getAllOrganizations() {

        return repo.findAll();
    }


    @GetMapping("/rest/api/organismes")
    @ResponseBody
    public List<Organisme> getAllOrganizationsAsJson() {
        List<Organisme> organismes = repo.findAll();
        return organismes;
    }


    @GetMapping(path = "/rest/organismes/{id}")
    public Organisme getOrganizationDetails(@PathVariable Long id)throws NoSuchElementException {
        return repo.findById(id).orElseThrow();
    }


    @PostMapping("/rest/organismes")
    public Organisme addOrganization(@RequestBody Organisme newOrganization) {
        return repo.save(newOrganization);
    }


    @PutMapping("/rest/updateOrganization/{id}")
    public Organisme updateOrganization(@RequestBody Organisme newOrganization, @PathVariable Long id) {
        return repo.findById(id)
                .map(organisme -> {
                    organisme.setLibelle(newOrganization.getLibelle());
                    organisme.setActivite(newOrganization.getActivite());
                    organisme.setAdresse(newOrganization.getAdresse());
                    organisme.setVille(newOrganization.getVille());
                    organisme.setCode_postal(newOrganization.getCode_postal());
                    organisme.setUrl(newOrganization.getUrl());
                    organisme.setLatitude(newOrganization.getLatitude());
                    organisme.setLongitude(newOrganization.getLongitude());
                    organisme.setEvenements(newOrganization.getEvenements());
                    return repo.save(organisme);
                })
                .orElseGet(() -> {
                    newOrganization.setId(id);
                    return repo.save(newOrganization);
                });
    }

    @DeleteMapping("/rest/organismes/{id}")
    public void deleteOrganization(@PathVariable Long id) {
        repo.deleteById(id);
    }


}
