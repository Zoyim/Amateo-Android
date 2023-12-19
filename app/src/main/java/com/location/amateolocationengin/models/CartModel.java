package com.location.amateolocationengin.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CartModel implements Serializable {
    @SerializedName("engin")
    private EnginModel engin;
    @SerializedName("quantity")
    private int quantity;

    public CartModel() {
    }

    public CartModel(EnginModel enginModel, int i) {
        this.engin = enginModel;
        this.quantity = i;
    }

    public EnginModel getEngin() {
        return this.engin;
    }

    public void setEngin(EnginModel enginModel) {
        this.engin = enginModel;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int i) {
        this.quantity = i;
    }
}
