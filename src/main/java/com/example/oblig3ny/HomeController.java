package com.example.oblig3ny;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {


    @Autowired
    private BillettRepository rep;



    @PostMapping("/lagre")
    public void lagreBillett(Billett innBillett){
        rep.lagreBillett(innBillett);
        }

    @GetMapping("/hentAlle")
    public List<Billett> hentAlle(){
        return rep.hentAlleBilletter();
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

