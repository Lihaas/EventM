package com.skywalker.android.apps.eventm.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skywalker.android.apps.eventm.Adapter.SearchAdapter;
import com.skywalker.android.apps.eventm.Model.SearchPojo;
import com.skywalker.android.apps.eventm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {

    EditText searchBar;
    RecyclerView recyclerView;
    List<SearchPojo> mUsers;
    SearchAdapter adapter;
    String search_string;
    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_search,container,false);



        searchBar = rootView.findViewById(R.id.searchBar);
        recyclerView = rootView.findViewById(R.id.search_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mUsers=new ArrayList<>();
        adapter = new SearchAdapter(mUsers,getActivity());
        recyclerView.setAdapter(adapter);

        readuser();

       searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        return rootView;
    }

    private void readuser(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(searchBar.getText().toString().equals("")){
                    mUsers.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                        SearchPojo user=snapshot1.getValue(SearchPojo.class);
                        mUsers.add(user);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchUsers(String s){
               search_string = s;

        Query query2= FirebaseDatabase.getInstance().getReference("Events").child("TechEvent").orderByChild("EventName")
                .startAt(s)
                .endAt(s+"\uf8ff");


            query2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mUsers.clear();
                    if(dataSnapshot.getChildrenCount() == 0){
                        SearchInDance(search_string);
                    }
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                        SearchPojo user=snapshot.getValue(SearchPojo.class);
                        mUsers.add(user);

                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


    }




    public void SearchInDance(String s){

        Query query= FirebaseDatabase.getInstance().getReference("Events").child("DanceEvent").orderByChild("EventName")
                .startAt(s)
                .endAt(s+"\uf8ff");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mUsers.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                        SearchPojo user=snapshot.getValue(SearchPojo.class);
                        mUsers.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




    }


}
