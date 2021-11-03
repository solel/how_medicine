package com.example.how_medicine;

import java.io.Serializable;

public class MedicineItem implements Serializable {
    String name;
    String company;
    String efficacy;
    String use;
    String know1;
    String know2;
    String knowMedi;
    String sideEffect;
    String keep;

    public MedicineItem(String name, String company, String efficacy, String use, String know1, String know2, String knowMedi, String sideEffect, String keep){
        this.name=name;
        this.company=company;

        this.efficacy=efficacy;
        this.use=use;
        this.know1=know1;
        this.know2=know2;
        this.knowMedi=knowMedi;
        this.sideEffect=sideEffect;
        this.keep=keep;
    }

/*    public MedicineItem(String efficacy, String use, String know1, String know2, String knowMedi, String sideEffect, String keep){
        this.efficacy=efficacy;
        this.use=use;
        this.know1=know1;
        this.know2=know2;
        this.knowMedi=knowMedi;
        this.sideEffect=sideEffect;
        this.keep=keep;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getKnow1() {
        return know1;
    }

    public void setKnow1(String know1) {
        this.know1 = know1;
    }

    public String getKnow2() {
        return know2;
    }

    public void setKnow2(String know2) {
        this.know2 = know2;
    }

    public String getKnowMedi() {
        return knowMedi;
    }

    public void setKnowMedi(String knowMedi) {
        this.knowMedi = knowMedi;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getKeep() {
        return keep;
    }

    public void setKeep(String keep) {
        this.keep = keep;
    }

}
