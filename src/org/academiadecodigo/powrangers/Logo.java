package org.academiadecodigo.powrangers;

import java.util.Random;

public enum Logo {

    ACADEMIADECODIGO,
    ADIDAS,
    ANDROID,
    APPLE,
    DOMINOS,
    FACEBOOK,
    GMAIL,
    ITUNES,
    JAVA,
    MCDONALDS,
    NETFLIX,
    NIKE,
    PLAYBOY,
    PLAYSTATION,
    SKYPE,
    SPOTIFY,
    TWITTER,
    WINDOWS,
    XBOX,
    YOUTUBE;

    public static Logo logoRandom() {

        int random = (int) (Math.random() * Logo.values().length);
        Logo ligo = Logo.values()[random];
        //System.out.println(ligo);
            return Logo.values()[new Random().nextInt(Logo.values().length)];
    }

}