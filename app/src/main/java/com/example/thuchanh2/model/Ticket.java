package com.example.thuchanh2.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ticket implements Serializable {
    private Integer id;
    private String name;
    private Integer depart;
    private Date date;
    private Integer price;
    private Boolean hasPackage;

    public Integer getId() {
        return id;
    }

    public String getIdStr() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPriceStr() {
        return price.toString();
    }

    public Integer getDepart() {
        return depart;
    }

    public String getDepartStr() {
        return depart.toString();
    }

    public Date getDate() {
        return date;
    }

    public Boolean getHasPackage() {
        return hasPackage;
    }

    public String getHasPackageStr() {
        return hasPackage.toString();
    }


    public String getDateStr() {
        return format.format(date);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepart(Integer depart) {
        this.depart = depart;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            this.date = new Date();
        }
    }

    public void setHasPackage(String gender) {
        this.hasPackage = Boolean.parseBoolean(gender);
    }

    public void setHasPackage(Boolean gender) {
        this.hasPackage = gender;
    }


    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
}
