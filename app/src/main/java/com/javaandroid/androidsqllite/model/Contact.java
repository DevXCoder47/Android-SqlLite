package com.javaandroid.androidsqllite.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private int Id;
    private String Name;
    private String Number;
    private String Email;
    public Contact(){}
    public Contact(int Id, String Name, String Number, String Email){
        this.Id = Id;
        this.Name = Name;
        this.Number = Number;
        this.Email = Email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
