package com.example.empresa_app_android.Classes;

import com.google.gson.annotations.SerializedName;

public class Empresa_lista {

    @SerializedName("enterprise_type_name")
    String enterprise_type_name;
    @SerializedName("photo")
    String photo;
    @SerializedName("country")
    String country;
    @SerializedName("description")
    String description;
    @SerializedName("enterprise_name")
    String enterprise_name;

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getEnterprise_type_name() {
        return enterprise_type_name;
    }

    public void setEnterprise_type_name(String enterprise_type_name) {
        this.enterprise_type_name = enterprise_type_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
