package org.example.model;

public enum Gender {
    MALE, FEMALE;

    public static Gender fromName(String name){
        if(name == null) return null;

        return valueOf(name);
    }
}
