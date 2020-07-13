package com.szadst.szoemhost_and.Domaine;

import java.io.Serializable;

public class Eleves implements Serializable {

    private int id;
    private String nom;
    private String prenom;
    private String classe;
    private String matricule;

    public Eleves() {
    }

    public Eleves(int id, String nom, String prenom, String classe, String matricule) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMatricule() {
        return matricule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
