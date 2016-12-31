package com.example.radibarq.pokemongotradecenter;

import android.net.Uri;

/**
 * Created by radibarq on 10/24/16.
 */

public class User {

    String displayName;
    String email;
    Uri photoUrl;
    String id;

    User(String displayName, String email, Uri photoUrl, String id)
    {

        this.displayName = displayName;

        this.email = email;

        this.photoUrl = photoUrl;

        this.id = id;

    }

    User()
    {

    }



}
