package com.skywalker.android.apps.eventm.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skywalker.android.apps.eventm.Adapter.ReciverAdapter;
import com.skywalker.android.apps.eventm.Adapter.SenderAdapter;
import com.skywalker.android.apps.eventm.Model.RequestReciver;
import com.skywalker.android.apps.eventm.Model.RequestSender;
import com.skywalker.android.apps.eventm.R;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification extends Fragment {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<RequestReciver> requestList = new ArrayList<>();;
    RecyclerView.Adapter adapters;


    public Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        adapters = new ReciverAdapter(requestList,getActivity());
        database = FirebaseDatabase.getInstance();
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference =database.getReference("User").child(currentuser).child("RequestRecive");

        recyclerView = rootView.findViewById(R.id.recycle4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestList.removeAll(requestList);
                //All data change in database notify here
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    //Add data to pojo class
                    RequestReciver requestdata = dataSnapshot1.getValue(RequestReciver.class);
                    requestList.add(requestdata);

                }
                //Saying to adapter on chnage on data
                adapters.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapters = new ReciverAdapter(requestList, getApplicationContext());
        recyclerView.setAdapter(adapters);


        return rootView;
    }

}
