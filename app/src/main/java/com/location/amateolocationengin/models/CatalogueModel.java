package com.location.amateolocationengin.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CatalogueModel {

    private int id;
    private String imageCat;
    private String nom;
    private int read;
    private TypeEngin typeEngin;
    @SerializedName("typeengin")
    private List<TypeEngin> typeEngine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageCat() {
        return imageCat;
    }

    public void setImageCat(String imageCat) {
        this.imageCat = imageCat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getRead() {
        return this.read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public TypeEngin getTypeEngin() {
        return typeEngin;
    }

    public void setTypeEngin(TypeEngin typeEngin) {
        this.typeEngin = typeEngin;
    }

    public List<TypeEngin> getTypeEngine() {
        return typeEngine;
    }

    public void setTypeEngine(List<TypeEngin> typeEngine) {
        this.typeEngine = typeEngine;
    }
}
