package com.example.serhio.substantiv.entities;

/**
 * Created by Serhio on 01.03.2018.
 */

public class Substantiv {
    private String substantivName;
    private  Gender gender;

    public Substantiv(String name, Gender gender){
        substantivName = name;
        this.gender = gender;
    }

    public String getSubstantivName() {
        return substantivName;
    }

    public void setSubstantivName(String substantivName) {
        this.substantivName = substantivName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
