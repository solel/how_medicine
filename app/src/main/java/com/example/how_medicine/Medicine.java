package com.example.how_medicine;

import androidx.room.ColumnInfo;
import androidx.room.Room;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//name company efficacy use know1 know2 knowMedi sideEffect keep
@Entity(tableName = "takemedicine")
public class Medicine {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String name;
    private String company;
    private String efficacy;
    private String use;
    private String know1;
    private String know2;
    private String knowMedi;
    private String sideEffect;
    private String keep;


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


    /*@ColumnInfo(name="name")
    public String name;

    public void Name(String name) {this.name = name;}

    public String getWord(){return this.name;}

    @ColumnInfo(name="company")
    public String company;

    @ColumnInfo(name="efficacy")
    public String efficacy;

    @ColumnInfo(name="use")
    public String use;

    @ColumnInfo(name="know1")
    public String know1;

    @ColumnInfo(name="know2")
    public String know2;

    @ColumnInfo(name="knowMedi")
    public String knowMedi;

    @ColumnInfo(name="sideEffect")
    public String sideEffect;

    @ColumnInfo(name="keep")
    public String keep;*/

}
