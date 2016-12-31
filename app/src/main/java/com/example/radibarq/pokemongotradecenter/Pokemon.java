package com.example.radibarq.pokemongotradecenter;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by radibarq on 10/11/16.
 */

public class Pokemon {


    public String Pokemon;
    public String Cp;
    public String Location;
    public String PublicName;




    public Pokemon() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public Pokemon(String Pokemon, String Cp, String Location, String PublicName) {

        this.Pokemon = Pokemon;
        this.Cp = Cp;
        this.Location = Location;
        this.PublicName = PublicName;
    }


    @Exclude
    public Map<String, Object> toMap()
    {

        HashMap<String, Object> result = new HashMap<>();

        result.put("pokemon", Pokemon);
        result.put("cp", Cp);
        result.put("location", Location);
        result.put("PublicName", PublicName);

        return result;

    }

}