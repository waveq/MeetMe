package com.waveq.meetme.controllers;

import java.io.Serializable;

public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1520318172495977648L;

    public String redirectToCreateAcc() {
        return "createacc";
    }

    public String redirectToAddPlace() {
        return "addplace";
    }

    public String redirectToShowPlaces() {
        return "showplaces";
    }

    public String redirectToAddEvent() {
        return "addevent";
    }

    public String redirectToShowEvents() {
        return "showevents";
    }

    public String redirectToIndex() {
        return "toindex";
    }

    public String redirectToSigns() {
        return "tosigns";
    }
    
     public String redirectToShowUsers() {
        return "showusers";
    }
}