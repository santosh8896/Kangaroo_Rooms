package com.example.kangaroo_rooms.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangaroo_rooms.Activities.LoginActivity;
import com.example.kangaroo_rooms.Adapters.NewAdapter;
import com.example.kangaroo_rooms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

//
    private RecyclerView mRecyclerView;
    private NewAdapter mAdapter;
    private TextView mOyoTextView;
    public static TextView mwelcomeMessageTxtView;
    static RelativeLayout chooseType;
    private TextView mSearchBarTextView;
    List<String> cityNames;
    int[] city_imgs;
    Context homeContext;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        //


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setPadding(0, 0, 0, 0);

        DatabaseReference cityNamesReference = FirebaseDatabase.getInstance().getReference();

        cityNamesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                cityNames = new ArrayList<String>();
                cityNames.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    if ("Users".equals(dataSnapshot1.getKey()) || "Vendors".equals(dataSnapshot1.getKey()) || "Bookings".equals(dataSnapshot1.getKey()) || "Admins".equals(dataSnapshot1.getKey()) || "Coupons".equals(dataSnapshot1.getKey())){

                        //do nothing

                    }
                    else {

                        cityNames.add(dataSnapshot1.getKey());

                    }

                }

                //String[] items = { "Nearby", "coachings","schools","others"};

                city_imgs = new int[cityNames.size()];

                for (int i=0;i<cityNames.size();i++){

                    city_imgs[i] = R.drawable.ic_location_city_black_24dp;

                }

                mRecyclerView = (RecyclerView) view.findViewById(R.id.rvToDoList);
                mAdapter = new NewAdapter(getActivity(),cityNames,city_imgs,getResources());
                mRecyclerView.setAdapter(mAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setNestedScrollingEnabled(false);
                ViewCompat.setNestedScrollingEnabled(mRecyclerView,false);

                mOyoTextView = (TextView) view.findViewById(R.id.first_activity_oyo_txt_view);
                mwelcomeMessageTxtView = (TextView) view.findViewById(R.id.welcome_msg) ;

                mOyoTextView.setTypeface(custom_font);
                mwelcomeMessageTxtView.setText(welcomeMessage+", "+ LoginActivity.userName);

                mSearchBarTextView = (TextView) view.findViewById(R.id.sb);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }


}
