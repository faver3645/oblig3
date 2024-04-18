package com.example.oblig3ny;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class BillettRepository {
    @Autowired
    private JdbcTemplate db;

    public void lagreBillett(Billett innBillett){
        String sql = "INSERT INTO Billett (film, antall, fornavn, etternavn, telefonnr, epost)" +
                "VALUES (?,?,?,?,?,?)";
        db.update(sql, innBillett.getFilm(), innBillett.getAntall(), innBillett.getFornavn(),
                innBillett.getEtternavn(), innBillett.getTelefonnr(), innBillett.getEpost());
    }

    public List<Billett> HentAlleBilletter(){
        String sql = "SELECT * FROM Billett";
        List<Billett> AlleBilletter = db.query(sql,new BeanPropertyRowMapper(Billett.class));
        Collections.sort(AlleBilletter);
        return AlleBilletter;
    }

    public void slettAlleBilletter(){
        String sql = "DELETE FROM Billett";
        db.update(sql);
    }

    public List<Film> hentFilmer(){
        String sql = "SELECT * FROM Film";
        List<Film> hentFilm = db.query(sql, new BeanPropertyRowMapper(Film.class));
        return hentFilm;
    }
}
