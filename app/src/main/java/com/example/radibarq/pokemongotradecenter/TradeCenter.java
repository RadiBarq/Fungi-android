package com.example.radibarq.pokemongotradecenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TradeCenter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TradeCenter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TradeCenter extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView textView;
    private ProgressDialog pDialog;
    JSONArray peoples = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
        ArrayList<HashMap<String, String>> personList;
    ListView list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int CHEKER = 0;
    int COUNTER = 0;
        String myJSON;

    private FragmentActivity myContext;




    private OnFragmentInteractionListener mListener;

    public TradeCenter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TradeCenter.
     */
    // TODO: Rename and change types and number of parameters
    public static TradeCenter newInstance(String param1, String param2) {
        TradeCenter fragment = new TradeCenter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_trade_center, container, false);


        textView = (TextView) v.findViewById(R.id.textView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);


        list = (ListView) v.findViewById(R.id.list);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CHEKER = 1;
                personList = new ArrayList<HashMap<String, String>>();
                getData();
            }
        });

        personList = new ArrayList<HashMap<String, String>>();
        getData();

        // TODO

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trade_center, container, false);

        // Inflate the layout for this fragment

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void getData()
    {
        class GetDataJSON extends AsyncTask<String, Void, String>
        {

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                pDialog = new ProgressDialog(getContext());
                pDialog.setMessage("Loading Pokemons Nearby...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

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
                mSwipeRefreshLayout.setRefreshing(false);

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


                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put("id", id);
                persons.put("pokemon", pokemon);
                persons.put("cp", cp);
                persons.put("location", location);
                persons.put("publicName", publicName);



                personList.add(persons);


            }
            ListAdapter adapter = new SimpleAdapter(
                   getActivity(), personList, R.layout.list_item,
                    new String[]{"id", "pokemon", "location", "publicName", "cp"},
                    new int[]{R.id.id, R.id.description, R.id.likes, R.id.date, R.id.area}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
