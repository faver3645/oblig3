package com.example.oblig3ny;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {


    @Autowired
    private BillettRepository rep;

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    private boolean validerBillett(Billett billett){
        String regexFilm = "[a-zA-ZæøåÆØÅ. \\-]{2,10}";
        String regexAntall = "[1-9]\\d*";
        String regexFornavn = "[A-ZÆØÅa-zæøå. -]{2,50}";
        String regexEtternavn = "[A-ZÆØÅa-zæøå. -]{2,50}";
        String regexTelefonnr = "[0-9]{8}";
        String regexEpost = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        boolean filmOK = billett.getFilm().matches(regexFilm);
        String antallString = String.valueOf(billett.getAntall());
        boolean antallOK = antallString.matches(regexAntall);
        boolean fornavnOK = billett.getFornavn().matches(regexFornavn);
        boolean etternavnOk = billett.getEtternavn().matches(regexEtternavn);
        boolean telefonnrOK = billett.getTelefonnr().matches(regexTelefonnr);
        boolean epostOK = billett.getEpost().matches(regexEpost);
        if(filmOK && antallOK && fornavnOK && etternavnOk && telefonnrOK && epostOK){
            return true;
        }
        else {
            logger.error("Valideringsfeil!");
            return false;
        }
    }

    @PostMapping("/lagre")
    public void lagreBillett(Billett innBillett, HttpServletResponse response) throws IOException {
        if(!validerBillett(innBillett)){
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value(),"Valideringsfeil - prøv igjen");
        }
        else{
            rep.lagreBillett(innBillett);
        }
    }

    @GetMapping("/hentAlle")
    public List<Billett> hentAlle(){
        return rep.HentAlleBilletter();
    }

    @GetMapping("/slettAlle")
    public void slettAlle(){
        rep.slettAlleBilletter();
    }

    @GetMapping("/hentAlleFilmer")
    public List<Film> hentFilmer(){
        return rep.hentFilmer();
    }
}

