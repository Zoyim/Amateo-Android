package com.location.amateolocationengin.models;

import com.google.gson.annotations.SerializedName;

public class PartenaireModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("prenom")
    private String prenom;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
