package org.example.model;

public enum MaritalStatus {
    MARRIED, SINGLE;

    public static MaritalStatus fromName(String name){
        if(name == null) return null;

        return valueOf(name);
    }
}
