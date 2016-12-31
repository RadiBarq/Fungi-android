package com.example.radibarq.pokemongotradecenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentClicked.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentClicked#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClicked extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    CircleImageView circleImageView;
    TextView textViewName;
    TextView textViewEmail;
    Button buttonLocation;
    Button buttonContact;
    DatabaseReference myRef;

    public FragmentClicked() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentClicked.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentClicked newInstance(String param1, String param2) {
        FragmentClicked fragment = new FragmentClicked();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_clicked, container, true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getInstance().getReference("Pokemons");


        int selfClickCheck = 0;

         textViewName = (TextView) v.findViewById(R.id.name);
         textViewEmail = (TextView) v.findViewById(R.id.email);


        circleImageView = (CircleImageView) v.findViewById(R.id.circleView);

        buttonLocation = (Button) v.findViewById(R.id.buttonLocation);



        buttonContact = (Button) v.findViewById(R.id.buttonContact);

        if (MainActivity.currentUser.displayName.matches(LoginActivity.user.displayName))
        {
            selfClickCheck = 1;
        }


        else
        {
            buttonLocation.setVisibility(View.GONE);
        }


        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                        myRef.child(MainActivity.uniqueKey).removeValue();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                getActivity().onBackPressed();

            }
        });

        if (selfClickCheck == 0) {
            buttonContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Chat.class);
                    startActivity(intent);
                }
            });
        }


        textViewName.setText(MainActivity.currentUser.displayName);


        //textViewEmail.setText(MainActivity.currentUser.email);
        //Picasso.with(getActivity()).load(MainActivity.currentUser.photoUrl).into((circleImageView));

        return inflater.inflate(R.layout.fragment_fragment_clicked, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

            ///circleImageView.setImageURI(LoginActivity.user.photoUrl);



        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        boolean areAllItemsEnabled();

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
