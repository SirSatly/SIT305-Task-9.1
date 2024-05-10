package com.example.lostandfound;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Advert {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public Advert(String id, String name, String phone, String description, String date, String location, String type)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.location = location;
        this.type = type;
        try {
            this.date = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Advert(String name, String phone, String description, Date date, String location, String type) {
        this.id = System.currentTimeMillis()+"";
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.type = type;
    }

    private final String id;
    public String getId() {
        return id;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String phone;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    private Date date;
    public  Date getDate() {
        return date;
    }
    public String getDateString() {
        return DATE_FORMAT.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }

    private String location;
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    private String type;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}