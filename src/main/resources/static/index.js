$(function(){
    hentAlleFilmer();
    hentAlle();
});

function hentAlleFilmer(){
    $.get("/hentAlleFilmer", function(filmer){
        formaterFilmer(filmer);
    });
}

function formaterFilmer(filmer){
    let ut = "<select id='valgtFilm'>";
    ut += "<option value='' disabled selected>Velg film her</option>";
    for(const film of filmer){
        ut += "<option>"+ film.film+"</option>";
    }
    ut += "</select>";
    $("#film").html(ut);
}
function lagreIArray(){
    sendBillett();
}

function hentAlle(){
    $.get("/hentAlle", function(data){
        formaterBillett(data);
    });
}
function formaterBillett(billett){
    let ut =  "<table class='table table-striped'><tr>" +
        "<th>Film</th><th>Antall</th><th>Fornavn</th><th>Etternavn" +
        "</th><th>Telefonnr</th><th>Epost</th>" +
        "</tr>";
    for(let b of billett){
        ut+="<tr>";
        ut += "<td>"+b.film+"</td><td>"+b.antall+
            "</td><td>"+b.fornavn+"</td><td>"+b.etternavn+"</td><td>"+b.telefonnr+"</td>" +
            "<td>"+b.epost+"</td>";
        ut+="</tr>";
    }
    ut += "</table>";
    $("#utBillett").html(ut);
}
function slettBillett(){
    $.get("/slettAlle", function(){
        hentAlle();
    });
}

function validerFilm(film){
    if(film){
        $("#feilFilm").html("");
        return true;
    }
    else{
        $("#feilFilm").html("Fyll ut feltet");
        return false;
    }
}

function validerAntall(antall){
    let regex = /^[1-9]\d*$/;
    if(regex.test(antall)){
        $("#feilAntall").html("");
        return true;
    }
    else{
        $("#feilAntall").html("Bruk kun tall");
        return false;
    }
}
function validerNavn(fornavn){
    let regex = /^[A-ZÆØÅa-zæøå. /-]{2,50}$/;
    if(regex.test(fornavn)){
        $("#feilFornavn").html("");
        return true;
    }
    else {
        $("#feilFornavn").html("Bruk kun store og små bokstaver, og .,- og mellomrom");
        return false;
    }
}

function validerEtternavn(etternavn){
    let regex = /^[A-ZÆØÅa-zæøå. /-]{2,50}$/;
    if(regex.test(etternavn)){
        $("#feilEtternavn").html("");
        return true;
    }
    else {
        $("#feilEtternavn").html("Bruk kun store og små bokstaver, og .,- og mellomrom");
        return false;
    }
}

function validerTelefonnr (telefonnr){
    let regex = /^[0-9]{8}$/;
    if(regex.test(telefonnr)){
        $("#feilTelefonnr").html("");
        return true;
    }
    else{
        $("#feilTelefonnr").html("Må være åtte sifre");
        return false;
    }
}

function validerEpost (epost){
    let regex = /^[A-Za-z0-9._%+/-]+@[A-Za-z0-9./-]+\.[A-Za-z]{2,}$/;
    if(regex.test(epost)){
        $("#feilEpost").html("");
        return true;
    }
    else{
        $("#feilEpost").html("Skriv inn en gyldig e-postadresse");
        return false;
    }
}

function validerBillett(billett){
    filmOK = validerFilm(billett.film);
    antallOK = validerAntall(billett.antall);
    fornavnOK = validerNavn(billett.fornavn);
    etternavnOK = validerEtternavn(billett.etternavn);
    telefonnrOK = validerTelefonnr(billett.telefonnr);
    epostOK = validerEpost(billett.epost);

    if(filmOK && antallOK && fornavnOK && etternavnOK && telefonnrOK && epostOK){
        return true;
    }
    else{
        return false;
    }
}

function sendBillett() {
    const billett = {
        film: $("#valgtFilm").val(),
        antall: $("#antall").val(),
        fornavn: $("#fornavn").val(),
        etternavn: $("#etternavn").val(),
        telefonnr: $("#telefonnr").val(),
        epost: $("#epost").val()
    };

    if (validerBillett(billett)) {
        $.post("/lagre", billett, function () {
            hentAlle();
        });
        $("#valgtFilm").val("");
        $("#antall").val("");
        $("#fornavn").val("");
        $("#etternavn").val("");
        $("#telefonnr").val("");
        $("#epost").val("");
    }
    else
        {
            $("#utBillett").html("Fyll ut alle felter og rett alle feil i skjemaet før innsending!")
        }
    }
