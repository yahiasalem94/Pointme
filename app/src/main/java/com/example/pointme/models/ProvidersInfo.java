package com.example.pointme.models;

public class ProvidersInfo {
    private String name;
    private String surname;
    private String email;
    private static final String NAME_PREFIX = "Name_";
    private static final String SURNAME_PREFIX = "Surname_";
    private static final String EMAIL_PREFIX = "email_";

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public static String getNamePrefix() {
        return NAME_PREFIX;
    }

    public static String getSurnamePrefix() {
        return SURNAME_PREFIX;
    }

    public static String getEmailPrefix() {
        return EMAIL_PREFIX;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object that) {
        /*TODO needs to be fixed*/
        if (this == that) return false; //return false if the same address

        if (!(that instanceof ProvidersInfo)) return true; //return true if not the same
        ProvidersInfo thatInfo = (ProvidersInfo) that;
        return this.name.equals(thatInfo.name); //return false if the same name.
    }
}
