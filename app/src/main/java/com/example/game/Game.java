package com.example.game;

import java.util.List;
import java.util.Map;

import ca.roumani.i2c.Country;
import ca.roumani.i2c.CountryDB;

class Game {
    private final CountryDB db;

    // class constructor
    public Game() {
        this.db = new CountryDB();    // created instance of the CountryDB.

    }


    public String qa() {
        List<String> capitals = db.getCapitals();
        int n = capitals.size();
        int index = (int) (n * Math.random());
        String c = capitals.get(index);

        Map<String, Country> data = db.getData();
        Country ref = data.get(c);
        if (Math.random() < 0.5) {
            return  "What is the capital of " + ref.getName() + "?" + "\n" + ref.getCapital();
        }
        else {
            return ref.getCapital() + " is the capital of?" + "\n" + ref.getName();
        }


    }

}
