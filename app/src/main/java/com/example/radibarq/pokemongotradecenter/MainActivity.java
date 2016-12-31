package com.example.radibarq.pokemongotradecenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements Search.OnFragmentInteractionListener, TradeCenter.OnFragmentInteractionListener, FragmentClicked.OnFragmentInteractionListener, MessagesFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    Fragment fragment;
    ArrayList<HashMap<String, String>> messages;


    int index;
    private ProgressDialog pDialog;
    JSONArray peoples = null;
    //SwipeRefreshLayout mSwipeRefreshLayout;
    Class fragmentClass = null;
    String myJSON;
    ArrayList<HashMap<String, String>> personList;
    ArrayList<HashMap<String, String>> pokemons;
    ListAdapter adapter;

    // There is using whe the user click one of the list item.
    String displayName;
    static String uniqueKey;
    FloatingActionButton fab;

    int oneTimeCounter= 0 ;


    String removedUser;
    String removedMessageId;


    ListView list;
    int CHEKER = 0;
    int pokemonDrawableId;
    DatabaseReference myRef;
    DatabaseReference currentUserReference;

    public static User currentUser;

    LocationManager locationManager;

    String sLocation;

    int counter;

    DatabaseReference updateLocationRef;





    // Pokemon drawable id array
    Integer[] flags = new Integer[]{

            R.drawable.squirtle,
            R.drawable.bulbasaur,
            R.drawable.nidoqueen,
            R.drawable.abra,
            R.drawable.alkazam,
            R.drawable.arbok,
            R.drawable.arcanine,
            R.drawable.arodactyl,
            R.drawable.articuno,
            R.drawable.beedrill,
            R.drawable.bellsprout,
            R.drawable.butterfree,
            R.drawable.caterpie,
            R.drawable.chansey,
            R.drawable.charizard,
            R.drawable.charmander,
            R.drawable.charmeleon,
            R.drawable.clefable,
            R.drawable.clefairy,
            R.drawable.cloyster,
            R.drawable.cubone,
            R.drawable.dewgong,
            R.drawable.diglett,
            R.drawable.digtrio,
            R.drawable.ditto,
            R.drawable.dodrio,
            R.drawable.doduo,
            R.drawable.dragonair,
            R.drawable.dragonite,
            R.drawable.dratini,
            R.drawable.drowzee,
            R.drawable.eevee,
            R.drawable.ekans,
            R.drawable.electabuzz,
            R.drawable.electrode,
            R.drawable.exeggcute,
            R.drawable.exeggutor,
            R.drawable.farfetchd,
            R.drawable.fearow,
            R.drawable.flareon,
            R.drawable.gastly,
            R.drawable.gengar,
            R.drawable.geodude,
            R.drawable.gloom,
            R.drawable.golbar,
            R.drawable.golbat,
            R.drawable.goldcuk,
            R.drawable.goldeen,
            R.drawable.golem,
            R.drawable.graveler,
            R.drawable.grimer,
            R.drawable.growlithe,
            R.drawable.gyardos,
            R.drawable.haunter,
            R.drawable.hitmonchan,
            R.drawable.hitmonlee,
            R.drawable.horsea,
            R.drawable.hypno,
            R.drawable.ivysaur,
            R.drawable.jigglypuff,
            R.drawable.jolteon,
            R.drawable.jynx,
            R.drawable.kabuto,
            R.drawable.kabutops,
            R.drawable.kadabra,
            R.drawable.kakuna,
            R.drawable.kangaskhan,
            R.drawable.kingler,
            R.drawable.koffing,
            R.drawable.krabby,
            R.drawable.lapras,
            R.drawable.lickitung,
            R.drawable.machamp,
            R.drawable.machoke,
            R.drawable.machop,
            R.drawable.magikarp,
            R.drawable.magmar,
            R.drawable.magnemite,
            R.drawable.magneton,
            R.drawable.mankey,
            R.drawable.marowak,
            R.drawable.meowth,
            R.drawable.metapod,
            R.drawable.mew,
            R.drawable.mewtwo,
            R.drawable.moltres,
            R.drawable.mrmime,
            R.drawable.muk,
            R.drawable.nidoking,
            R.drawable.nidoqueen,
            R.drawable.nidoranf,
            R.drawable.nidorina,
            R.drawable.nidorino,
            R.drawable.ninetales,
            R.drawable.oddish,
            R.drawable.omanyte,
            R.drawable.omastar,
            R.drawable.onix,
            R.drawable.paras,
            R.drawable.parasect,
            R.drawable.picachu,
            R.drawable.pidgeot,
            R.drawable.pidgeotto,
            R.drawable.pidgey,
            R.drawable.picachu,
            R.drawable.pinsir,
            R.drawable.poliwag,
            R.drawable.poliwhirl,
            R.drawable.poliwrath,
            R.drawable.ponita,
            R.drawable.ponyta,
            R.drawable.porygon,
            R.drawable.primeape,
            R.drawable.psyduck,
            R.drawable.raichu,
            R.drawable.rapidash,
            R.drawable.raticate,
            R.drawable.rattata,
            R.drawable.rhydon,
            R.drawable.rhyhorn,
            R.drawable.sandshrew,
            R.drawable.sandslash,
            R.drawable.scyther,
            R.drawable.seaking,
            R.drawable.seedra,
            R.drawable.seel,
            R.drawable.shellder,
            R.drawable.slowbro,
            R.drawable.slowpoke,
            R.drawable.snorlax,
            R.drawable.spearow,
            R.drawable.squirtle,
            R.drawable.starmie,
            R.drawable.staryu,
            R.drawable.tangela,
            R.drawable.tauros,
            R.drawable.tentacool,
            R.drawable.vaporeon,
            R.drawable.venasaur,
            R.drawable.venat,
            R.drawable.venomoth,
            R.drawable.venonat,
            R.drawable.victreebel,
            R.drawable.vileplume,
            R.drawable.voltrop,
            R.drawable.vulpix,
            R.drawable.wartortle,
            R.drawable.weedle,
            R.drawable.weepinbell,
            R.drawable.weezing,
            R.drawable.wigglytuff,
            R.drawable.zapdos,
            R.drawable.zubat
    };

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = 0;

        /// Related to the diaglog
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Pokemons Nearby...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        database = FirebaseDatabase.getInstance();

        currentUser = new User();

        // Set a Toolbar to replace the ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // We use this to initailizd fragment when delete it.
        fragmentClass = FragmentClicked.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        updateLocationRef = database.getInstance().getReference("Pokemons");


        oneTimeCounter = 0;



        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(final Location location) {

                //TODO


                // to check weather the it's nearby pokemon or messages
                if (counter == 0) {




                sLocation = String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude());


                myRef.removeEventListener(postListener);

                updateLocationRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        // Let's see what we can do right now fellow
                        String key;


                        for (com.google.firebase.database.DataSnapshot child : dataSnapshot.getChildren()) {

                            // This is to get the key.

                            key = child.getKey().toString();


                            // Let's see what to do here
                            if (child.child("PublicName").getValue().toString().matches(LoginActivity.user.displayName)) {
                                updateLocationRef.child(key).child("Location").setValue(sLocation);
                                oneTimeCounter = 1;

                            }

                        }

                        myRef.addValueEventListener(postListener);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {

                Toast.makeText(MainActivity.this, "Please Enable Your Location", Toast.LENGTH_LONG).show();

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


        // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);


        myRef = database.getInstance().getReference("Pokemons");



        GeoFire geoFire = new GeoFire(myRef);










        currentUserReference = database.getReference("Users");

        pokemons = new ArrayList<HashMap<String, String>>();

        //getData();

        list = (ListView) findViewById(R.id.list);

        myRef.addValueEventListener(postListener);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    displayName = ((TextView) view.findViewById(R.id.date)).getText().toString();
                    uniqueKey = ((TextView) view.findViewById(R.id.id)).getText().toString();

                    // This just called one time and will call the postListener...
                    currentUserReference.addListenerForSingleValueEvent(postListener1);

                }
        });


       //fragmentClass = Search.class;
        //try {
        // fragment = (Fragment) fragmentClass.newInstance();
        //}
        //
        // catch (Exception e) {
        //
        // e.printStackTrace();


        // }


        // Insert the fragment by replacing any existing fragment
        // android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//        f//ragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        // ...From section above...
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view

        setupDrawerContent(nvDrawer);
        drawerToggle.syncState();

        //mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            // This is when the screen get refreshed...
          //  @Override
            //public void onRefresh() {
              //  CHEKER = 1;
                //personList = new ArrayList<HashMap<String, String>>();
                //getData();

                //myRef.addValueEventListener(postListener);

            //}
        //
        // });

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddPokemon.class);
                startActivity(intent);



                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();

            }
        });

        personList = new ArrayList<HashMap<String, String>>();

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

    }

        ValueEventListener postListener1 = new ValueEventListener() {

        @Override
        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

           for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren())
           {
                if (postSnapShot.getKey().matches(displayName))
                {

                    for (com.google.firebase.database.DataSnapshot postpostSnapShot : postSnapShot.getChildren())
                    {
                        if (postpostSnapShot.getKey().matches("displayName"))
                        {
                            currentUser.displayName = postpostSnapShot.getValue().toString();
                        }
                        else if (postpostSnapShot.getKey().matches("email"))
                        {
                            currentUser.email = postpostSnapShot.getValue().toString();
                        }

                        else if (postpostSnapShot.getKey().matches("id"))
                        {
                            currentUser.id = postpostSnapShot.getValue().toString();
                        }

                        else
                        {
                            //currentUser.photoUrl = postpostSnapShot.getValue(Uri.class);
                        }

                    }

                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().hide(fragment).commit();

                    fragmentManager.beginTransaction().replace(R.id.flContent1, fragment).commit();
                }
           }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked



        switch (menuItem.getItemId()) {

            case R.id.nav_first_fragment:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                counter = 1;
                fab.setVisibility(View.VISIBLE);
                break;

            //case R.id.nav_second_fragment:
              //  fragmentClass = Search.class;
                //counter = 0;
                //fab.setVisibility(View.GONE);
                //break;

            case R.id.nav_third_fragment:
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                counter = 1;
                fab.setVisibility(View.VISIBLE);
                break;

            case R.id.nav_fourth_fragment:
                populateMessages();
                menuItem.setChecked(true);
                mDrawer.closeDrawers();
                setTitle("Messages");
                locationManager = null;
                registerForContextMenu(list);

                counter = 1;
                fab.setVisibility(View.GONE);
                break;

            case R.id.nav_fifth_fragment:
                locationManager = null;
                unregisterForContextMenu(list);
                counter = 1;
                list = (ListView) findViewById(R.id.list);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        displayName = ((TextView) view.findViewById(R.id.date)).getText().toString();
                        uniqueKey = ((TextView) view.findViewById(R.id.id)).getText().toString();

                        // This just called one time and will call the postListener...
                        currentUserReference.addListenerForSingleValueEvent(postListener1);

                    }
                });
                myRef = database.getInstance().getReference("Pokemons");
                populateMyPokemons();
                menuItem.setChecked(true);
                mDrawer.closeDrawers();
                setTitle("My Pokemons");
                fab.setVisibility(View.VISIBLE);
                break;
        }

        if (counter == 0) {

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list) {

            removedUser = ((TextView) v.findViewById(R.id.description)).getText().toString();
            removedMessageId = ((TextView) findViewById(R.id.id)).getText().toString();


            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.hold_list_view, menu);
        }
    }


    ValueEventListener postListener6 = new ValueEventListener() {


        @Override
        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {



        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {

            //Remember before we get here we first go on onCreateContextMenu

            case R.id.delete:
                myRef.child(removedUser).removeValue();
                myRef = database.getInstance().getReference("Chat");
                myRef.child(removedMessageId).removeValue();

                return true;

            case R.id.block:
                //TODO in future
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    ValueEventListener postListener = new ValueEventListener() {



        @Override
        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

            pokemons = new ArrayList<HashMap<String, String>>();


                index = list.getFirstVisiblePosition();



            for (com.google.firebase.database.DataSnapshot child : dataSnapshot.getChildren())
            {
                HashMap <String, String> pokemon = new HashMap<String, String>();

                // The Unique Id Of The Pokemon
                pokemon.put("Id", child.getKey().toString());

                for (com.google.firebase.database.DataSnapshot child2: child.getChildren())
                {
                    pokemon.put(child2.getKey(), child2.getValue().toString());

                    // To the photo name to the list
                    if (child2.getKey().matches("Pokemon"))
                    {
                        pokemon.put("Flag", Integer.toString(choosePokemon(child2.getValue().toString())));

                    }
                }
                // This is a list that containts all the elements that we need..
                pokemons.add(pokemon);
            }

            pDialog.dismiss();
           // mSwipeRefreshLayout.setRefreshing(false);


             adapter = new SimpleAdapter(
                    MainActivity.this, pokemons, R.layout.list_item,
                    new String[]{"Id", "Pokemon", "Location", "PublicName", "Cp", "Flag"},
                    new int[]{R.id.id, R.id.description, R.id.likes, R.id.date, R.id.area, R.id.thumbnail}
            );

            list.setAdapter(adapter);
            list.setSelection(index);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {


        }

    };


   ValueEventListener postListener4 = new ValueEventListener() {

        int checker  = 0;

        @Override
        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

            for (com.google.firebase.database.DataSnapshot child : dataSnapshot.getChildren())
            {
                HashMap <String, String> pokemon = new HashMap<String, String>();
                checker = 0;

                pokemon.put("Id", child.getKey().toString());

                for (com.google.firebase.database.DataSnapshot child2: child.getChildren())
                {

                    if (child2.getKey().toString().matches("PublicName") && !child2.getValue().toString().matches(LoginActivity.user.displayName))
                    {
                        checker = 1;
                        break;
                    }

                    pokemon.put(child2.getKey(), child2.getValue().toString());

                    // To the photo name to the list
                    if (child2.getKey().matches("Pokemon"))
                    {
                        pokemon.put("Flag", Integer.toString(choosePokemon(child2.getValue().toString())));

                    }
                }

                // This is a list that containts all the elements that we need..
                if (checker == 1)
                    continue;

                pokemons.add(pokemon);

            }

            pDialog.dismiss();
            // mSwipeRefreshLayout.setRefreshing(false);


            adapter = new SimpleAdapter(

                    MainActivity.this, pokemons, R.layout.list_item,
                    new String[]{"Id", "Pokemon", "Location", "PublicName", "Cp", "Flag"},
                    new int[]{R.id.id, R.id.description, R.id.likes, R.id.date, R.id.area, R.id.thumbnail}
            );

            list.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // This code to populate my pokemons
    void populateMyPokemons()
    {
        pokemons = new ArrayList<HashMap<String, String>>();

        myRef.addValueEventListener(postListener4);
    }

    public void getData() {

        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //pDialog = new ProgressDialog(MainActivity.this);
                //pDialog.setMessage("Loading Pokemons Nearby...");
                //pDialog.setIndeterminate(false);
                //pDialog.setCancelable(true);
                //pDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result = null;

                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost(LoginActivity.LINK + "/get-data.php");
                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;

                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                pDialog.dismiss();
                //mSwipeRefreshLayout.setRefreshing(false);

                if (myJSON != null)
                    showList();
            }

        }

        GetDataJSON g = new GetDataJSON();
        g.execute();

    }

    protected void showList()
    {
        try
        {
            JSONObject jsonObject = new JSONObject(myJSON);
            peoples = jsonObject.getJSONArray("result");

            for (int i = 0; i < peoples.length(); i++)
            {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString("id");
                String pokemon = c.getString("pokemon");
                String cp = c.getString("cp");
                String location = c.getString("location");
                String publicName = c.getString("publicName");
                pokemonDrawableId = choosePokemon(pokemon);
                HashMap<String, String> persons = new HashMap<String, String>();
                persons.put("id", id);
                persons.put("pokemon", pokemon);
                persons.put("cp", cp);
                persons.put("location", location);
                persons.put("publicName", publicName);
                persons.put("flag", Integer.toString(choosePokemon(pokemon)));
                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personList, R.layout.list_item,
                    new String[]{"id", "pokemon", "location", "publicName", "cp", "flag"},
                    new int[]{R.id.id, R.id.description, R.id.likes, R.id.date, R.id.area, R.id.thumbnail}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void closefragment() {


    }



    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        mDrawer.closeDrawers();

    }


    void populateMessages()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getInstance().getReference("Users").child(LoginActivity.user.displayName).child("chat");

        myRef.addValueEventListener(postListener2);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String key = ((TextView) view.findViewById(R.id.id)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.description)).getText().toString();

                Chat.key = key;
                Chat.name = name;

                Chat.active = 1;

                Intent intent = new Intent(MainActivity.this, Chat.class);
                startActivity(intent);

            }
        });

    }

    ValueEventListener postListener2 = new ValueEventListener() {

        @Override
        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

            messages = new ArrayList<HashMap<String, String>>();

            for (com.google.firebase.database.DataSnapshot child : dataSnapshot.getChildren())
            {
                HashMap <String, String> message = new HashMap<String, String>();

                message.put("User", child.getKey().toString());
                message.put("Key", child.getValue().toString());
                messages.add(message);

            }

            adapter = new SimpleAdapter(

                    MainActivity.this, messages, R.layout.list_item,
                    new String[]{"User", "Key"},
                    new int[]{R.id.description, R.id.id}
            );

            list.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    private Integer choosePokemon(String pokemon)
    {
        Integer pokemonDrawableId = 0;

        switch (pokemon)
        {
            case "Bulbasaur":
                return R.drawable.bulbasaur;

            case "Ivysaur":
                return R.drawable.ivysaur;

            case "Venusaur":
                return R.drawable.venasaur;

            case "Charmander":
                return R.drawable.charmander;

            case "Charmeleon":
                return R.drawable.charmeleon;

            case "Charizard":
                return R.drawable.charizard;

            case "Squirtle":
                return R.drawable.squirtle;

            case "Wartortle":
                return R.drawable.wartortle;

            case "Blastoise":
                return R.drawable.blastoise;

            case "Caterpie":
                return R.drawable.caterpie;

            case "Metapod":
                return R.drawable.metapod;

            case "Butterfree":
                return R.drawable.butterfree;

            case "Weedle":
                return R.drawable.weedle;

            case "Kakuna":
                return R.drawable.weedle;

            case "Beedrill":
                return R.drawable.beedrill;

            case "Pidgey":
                return R.drawable.pidgey;

            case "Pidgeotto":
                return R.drawable.pidgeotto;

            case "Rattata":
                return R.drawable.rattata;

            case "Raticate":
                return R.drawable.raticate;

            case "Spearow":
                return R.drawable.spearow;

            case "Fearow":
                return R.drawable.fearow;

            case "Ekans":
                return R.drawable.ekans;

            case "Arbok":
                return R.drawable.arbok;

            case "Pikachu":
                return R.drawable.picachu;

            case "Raichu":
                return R.drawable.raichu;

            case "Sandshrew":
                return R.drawable.sandshrew;

            case "Sandslash":
                return R.drawable.sandslash;

            case "Nidoran(female)":
                return R.drawable.nidoranf;

            case "Nidorina":
                return R.drawable.nidorina;

            case "Nidoqueen":
                return R.drawable.nidoqueen;

            case "Nidoran(male)":
                return R.drawable.nidoran;

            case "Nidorino":
                return R.drawable.nidorino;

            case "Nidoking":
                return R.drawable.nidoking;

            case "Clefairy":
                return R.drawable.clefairy;

            case "Clefable":
                return R.drawable.clefable;

            case "Vulpix":
                return R.drawable.vulpix;

            case "Ninetales":
                return R.drawable.ninetales;

            case "Jigglypuff":
                return R.drawable.jigglypuff;

            case "Wigglytuff":
                return R.drawable.wigglytuff;

            case "Zubat":
                return R.drawable.zubat;

            case "Golbat":
                return R.drawable.golbat;

            case "Oddish":
                return R.drawable.oddish;

            case "Gloom":
                return R.drawable.gloom;

            case "Vileplume":
                return R.drawable.vileplume;

            case "Paras":
                return R.drawable.paras;

            case "Parasect":
                return R.drawable.parasect;

            case "Venonat":
                return R.drawable.venonat;

            case "Venomoth":
                return R.drawable.venomoth;

            case "Diglett":
                return R.drawable.diglett;

            case "Dugtrio":
                return R.drawable.digtrio;

            case "Meowth":
                return R.drawable.meowth;

            case "Persian":
                return R.drawable.persian;

            case "Psyduck":
                return R.drawable.psyduck;

            case "Golduck":
                return R.drawable.goldcuk;

            case "Mankey":
                return R.drawable.mankey;

            case "Primeape":
                return R.drawable.primeape;

            case "Growlithe":
                return R.drawable.growlithe;

            case "Arcanine":
                return R.drawable.arcanine;

            case "Poliwag":
                return R.drawable.poliwag;

            case "Poliwhirl":
                return R.drawable.poliwhirl;

            case "Poliwrath":
                return R.drawable.poliwrath;

            case "Abra":
                return R.drawable.abra;

            case "Kadabra":
                return R.drawable.kadabra;

            case "Alakazam":
                return R.drawable.alkazam;

            case "Machop":
                return R.drawable.machop;

            case "Machoke":
                return R.drawable.machoke;

            case "Machamp":
                return R.drawable.machamp;

            case "Bellsprout":
                return R.drawable.bellsprout;

            case "Weepinbell":
                return  R.drawable.weepinbell;

            case "Victreebel":
                return R.drawable.victreebel;

            case "Tentacool":
                return R.drawable.tentacool;

            case "Tentacruel":
                return R.drawable.tentacruel;

            case  "Geodude":
                return R.drawable.geodude;

            case "Graveler":
                return R.drawable.graveler;

            case "Golem":
                return R.drawable.golem;

            case "Ponyta":
                return R.drawable.ponyta;

            case "Rapidash":
                return R.drawable.rapidash;

            case "Slowpoke":
                return R.drawable.slowpoke;

            case "Slowbro":
                return R.drawable.slowbro;

            case "Magnemite":
                return R.drawable.magnemite;

            case "Magneton":
                return R.drawable.magneton;

            case "Farfetch\'d":
                return R.drawable.farfetchd;

            case "Doduo":
                return R.drawable.doduo;

            case "Dodrio":
                return R.drawable.dodrio;

            case "Seel":
                return R.drawable.seel;

            case "Dewgong":
                return R.drawable.dewgong;

            case "Grimer":
                return R.drawable.grimer;

            case "Muk":
                return R.drawable.muk;

            case "Shellder":
                return R.drawable.shellder;

            case "Cloyster":
                return R.drawable.cloyster;

            case "Gastly":
                return R.drawable.gastly;

            case "Gnager":
                return R.drawable.gengar;

            case "Onix":
                return R.drawable.onix;

            case "Drowzee":
                return R.drawable.onix;

            case "Hypno":
                return R.drawable.hypno;

            case "Krabby":
                return R.drawable.krabby;

            case "Kingler":
                return R.drawable.kingler;

            case "Voltrop":
                return R.drawable.voltrop;

            case "Electrode":
                return R.drawable.electrode;

            case "Exeggcute":
                return R.drawable.exeggcute;

            case "Cubone":
                return R.drawable.cubone;

            case "Marowak":
                return R.drawable.marowak;

            case "Hitmonlee":
                return R.drawable.hitmonlee;

            case "Hitmonchan":
                return R.drawable.hitmonchan;

            case "Lickitung":
                return R.drawable.lickitung;


            case "Koffing":
                return R.drawable.lickitung;


            case "Weezing":
                return R.drawable.weezing;

            case "Rhyhorn":
                return R.drawable.rhyhorn;


            case "Chansey":
                return R.drawable.chansey;


            case "Tangela":
                return R.drawable.tangela;


            case "Kangaskhan":
                return R.drawable.kangaskhan;


            case "Horsea":
                return R.drawable.horsea;


            case "Seadra":
                return R.drawable.seedra;


            case "Goldeen":
                return R.drawable.goldeen;


            case "Seaking":
                return R.drawable.seaking;


            case "Staryu":
                return R.drawable.staryu;


            case "Starmie":
                return R.drawable.starmie;


            case "Mr.Mime":
                return R.drawable.mrmime;


            case "Scyther":
                return R.drawable.scyther;


            case "Jynx":
                return R.drawable.jynx;


            case "Electabuzz":
                return R.drawable.electabuzz;


            case "Magmar":
                return R.drawable.magmar;



            case "Pinsir":
                return R.drawable.pinsir;


            case "Tauros":
                return R.drawable.tauros;


            case "Magikarp":
                return R.drawable.magikarp;


            case "Gyarados":
                return R.drawable.gyardos;



            case "Lapras":
                return R.drawable.lapras;


            case "Ditto":
                return R.drawable.ditto;


            case "Eevee":
                return R.drawable.eevee;


            case "Vaporeon":
                return R.drawable.vaporeon;


            case "Jolteon":
                return R.drawable.jolteon;


            case "Flareon":
                return R.drawable.flareon;



            case "Porygon":
                return R.drawable.porygon;



            case "Omanyte":
                return R.drawable.omanyte;


            case "Omastar":
                return R.drawable.omastar;


            case "Kabutpo":
                return R.drawable.kabuto;


            case "Kabutops":
                return R.drawable.kabutops;


            case "Aerodactyl":
                return R.drawable.arodactyl;


            case "Snorlax":
                return R.drawable.snorlax;



            case "Articuno":
                return R.drawable.articuno;



            case "Zapdos":
                return R.drawable.zapdos;


            case "Moltres":
                return R.drawable.moltres;



            case "Dratini":
                return R.drawable.dratini;



            case "Dragonair":
                return R.drawable.dragonair;



            case "Dragonite":
                return R.drawable.dragonite;


            case "Mewtwo":
                return R.drawable.mewtwo;


            case "Mew":
                return R.drawable.mew;
        }

        return pokemonDrawableId;

    }
}
