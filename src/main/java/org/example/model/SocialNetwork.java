package org.example.model;

public enum SocialNetwork {
    FACEBOOK, INSTAGRAM, TELEGRAM, VIBER, TWITTER;

    public static SocialNetwork fromName(String name){
        if(name == null) return null;

        return valueOf(name);
    }
}
