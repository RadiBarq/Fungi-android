package com.example.radibarq.pokemongotradecenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.kosalgeek.genasync12.*;
import com.kosalgeek.genasync12.MainActivity;

import java.util.HashMap;

public class AddPokemon extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    Spinner spinnerPokemon;
    EditText editTextCp;
    String stringPokemon;
    String stringCp;
    String sLocation;
    GoogleApiClient client;
    Location lastLocation;
    DatabaseReference myRef;
    boolean enabled_location;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);

        // This is related to the database reference...
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getInstance().getReference();


        // Let's use our code
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {

                // Here we got the location we want fellow.
                sLocation = String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {


            }
        };


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        editTextCp = (EditText) findViewById(R.id.editText);
        spinnerPokemon = (Spinner) findViewById(R.id.spinner);
    }


    public void onClickTrade(View view) {

        stringPokemon = spinnerPokemon.getSelectedItem().toString();
        stringCp = editTextCp.getText().toString();
        HashMap postData = new HashMap();

        enabled_location = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



      Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        sLocation = String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude());


        if (stringCp.matches("")) {

            Snackbar.make(view, "Please Enter A Valid Pokemon Cp!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

        else if (enabled_location == false)
        {
            Toast.makeText(this, "Please Enable Your Location", Toast.LENGTH_SHORT).show();
        }

        else {

            //postData.put("Pokemon", stringPokemon);
            //postData.put("Cp", stringCp);
            //postData.put("PublicName", LoginActivity.PUBLIC_NAME);
            //postData.put("Location", sLocation);

            // This is related to the firebase...
            addNewUser(stringPokemon, stringCp, sLocation, LoginActivity.user.displayName);

            Intent intent = new Intent(AddPokemon.this, com.example.radibarq.pokemongotradecenter.MainActivity.class);
            startActivity(intent);


            //PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData, true, new AsyncResponse() {
       //         @Override
     //           public void processFinish(String s) {

                  //  if (s.matches("success")) {

                      //  Intent intent = new Intent(AddPokemon.this, MainActivity.class);
                      //  startActivity(intent);

                   // }
               // }
           // });

            //ask.execute(LoginActivity.LINK + "/AddPokemon.php");
        }

   }


    private void addNewUser(String Pokemon, String Cp, String Location, String PublicName)
    {
        Pokemon pokemon = new Pokemon(Pokemon, Cp, Location, PublicName);



        String item = myRef.child("Pokemons").push().getKey();

        myRef.child("Pokemons").child(item).setValue(pokemon);

        String[] array = Location.split(" ");


        GeoFire geoFire = new GeoFire(myRef.child("items_location"));
        geoFire.setLocation(item, new GeoLocation(Double.parseDouble(array[0]) , Double.parseDouble(array[1])));

    }


    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        if(lastLocation != null)
        {
            sLocation = String.valueOf(lastLocation.getLatitude()) + " " + String.valueOf(lastLocation.getLongitude());
            client.disconnect();
        }

        if (lastLocation == null) {
            client.disconnect();

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(this, "Problem With Your Location Service, Please Try Again!", Toast.LENGTH_LONG).show();

    }


    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

}

