package com.location.amateolocationengin.models;

public class TypeEngin {
    private int categorie_id;

    private int id;
    private String imageType;
    private String nom;
    private String prixBas;
    private int read;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrixBas() {
        return prixBas;
    }

    public void setPrixBas(String prixBas) {
        this.prixBas = prixBas;
    }

    public int getRead() {
        return this.read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public TypeEngin(int id, String imageType, String nom, String prixBas) {
        this.id = id;
        this.imageType = imageType;
        this.nom = nom;
        this.prixBas = prixBas;
    }

    public TypeEngin() {

    }
}
