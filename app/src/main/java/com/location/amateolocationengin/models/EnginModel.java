package com.location.amateolocationengin.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class EnginModel implements Serializable {
    @SerializedName("energie")
    private String energie;
    @SerializedName("id")
    private int id;
    @SerializedName("imageEngin")
    private String imageEngin;
    @SerializedName("marque")
    private String marque;
    @SerializedName("nom")
    private String nom;
    @SerializedName("nomType")
    private String nomType;
    @SerializedName("nomVille")
    private String nomVille;
    @SerializedName("poidsTotal")
    private String poidsTotal;
    @SerializedName("poidsVide")
    private String poidsVide;
    @SerializedName("prix")
    private int prix;
    @SerializedName("prixLocation")
    private int prixLocation;
    @SerializedName("publication")
    private int publication;
    @SerializedName("puissance")
    private String puissance;
    @SerializedName("rmb")
    private int rmb;
    @SerializedName("statut")
    private int statut;
    @SerializedName("title")
    private String title;
    @SerializedName("vendu")
    private int vendu;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrixLocation() {
        return this.prixLocation;
    }

    public void setPrixLocation(int prixLocation) {
        this.prixLocation = prixLocation;
    }

    public int getPrix() {
        return this.prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getStatut() {
        return this.statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public String getEnergie() {
        return this.energie;
    }

    public void setEnergie(String energie) {
        this.energie = energie;
    }

    public String getPoidsVide() {
        return this.poidsVide;
    }

    public void setPoidsVide(String poidsVide) {
        this.poidsVide = poidsVide;
    }

    public String getPoidsTotal() {
        return this.poidsTotal;
    }

    public void setPoidsTotal(String poidsTotal) {
        this.poidsTotal = poidsTotal;
    }

    public String getImageEngin() {
        return this.imageEngin;
    }

    public void setImageEngin(String imageEngin) {
        this.imageEngin = imageEngin;
    }

    public String getPuissance() {
        return this.puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public String getMarque() {
        return this.marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getPublication() {
        return this.publication;
    }

    public void setPublication(int publication) {
        this.publication = publication;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRmb() {
        return this.rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public String getNomType() {
        return this.nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public String getNomVille() {
        return this.nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public int getVendu() {
        return this.vendu;
    }

    public void setVendu(int vendu) {
        this.vendu = vendu;
    }

    public EnginModel() {
    }

    public EnginModel(int id, String nom, int prixLocation, int prix, int statut, String energie, String poidsVide, String poidsTotal, String imageEngin, String puissance, String marque, int publication, String title, String nomVille, int vendu) {
        this.id = id;
        this.nom = nom;
        this.prixLocation = prixLocation;
        this.prix = prix;
        this.statut = statut;
        this.energie = energie;
        this.poidsVide = poidsVide;
        this.poidsTotal = poidsTotal;
        this.imageEngin = imageEngin;
        this.puissance = puissance;
        this.marque = marque;
        this.publication = publication;
        this.title = title;
        this.nomVille = nomVille;
        this.vendu = vendu;
    }
}
