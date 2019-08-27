package com.skywalker.android.apps.eventm.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skywalker.android.apps.eventm.MainActivity;
import com.skywalker.android.apps.eventm.Model.DetailPojo;
import com.skywalker.android.apps.eventm.Model.TechPojo;
import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    CircleImageView imgg;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView name_of_person,profession,codeOfUser;
    DetailPojo Datas;
    Button paymentDetial,SearchDeatil,passDetail;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profiles, container, false);

      //  pic_card = rootView.findViewById(R.id.pic_cardVIew);
        imgg = rootView.findViewById(R.id.profile_image2);
        name_of_person = rootView.findViewById(R.id.nameOfPerson2);
        profession = rootView.findViewById(R.id.profesionOfperson);
        codeOfUser = rootView.findViewById(R.id.personCode);
        passDetail = rootView.findViewById(R.id.passDetail);
        SearchDeatil = rootView.findViewById(R.id.teamDetail);
        paymentDetial = rootView.findViewById(R.id.paymentDetial);

        imgg.setClickable(false);

        database = FirebaseDatabase.getInstance();
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference =database.getReference().child("User").child(currentuser);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Datas = dataSnapshot.getValue(DetailPojo.class);
               try{
                if(!Datas.getmImageUrl().isEmpty()) {
                    Picasso.get().load(Datas.getmImageUrl()).into(imgg);
                    imgg.setClickable(true);
                }else {
                    imgg.setBackgroundResource(R.drawable.aaaaaaaa);
                }}
               catch (Exception e){}


               try {

                   if(!Datas.getmName().isEmpty()){
                       name_of_person.setText(Datas.getmName());
                   }else {
                       name_of_person.setText("Person Name");
                   }

               }catch (Exception e){}

               try {
               if(!Datas.getmProfesion().isEmpty()) {
                   profession.setText(Datas.getmProfesion());
               }
               else{
                   profession.setText("Profesion of User");
               }
               }catch (Exception e){}



                codeOfUser.setText(""+Datas.getEventId());





            }






            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        paymentDetial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toasty.warning(getActivity(), "You didn't do any payment", Toast.LENGTH_LONG, true).show();
            }
        });

        passDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainActivity.class);
                i.putExtra("passDetial",2);
                startActivity(i);
            }
        });

        SearchDeatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainActivity.class);
                i.putExtra("passDetial",3);
                startActivity(i);
            }
        });

        imgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ImageUpload fragment = new ImageUpload();
                Bundle bundle = new Bundle();
                if(!Datas.getmImageUrl().isEmpty()){
                bundle.putString("img",Datas.getmImageUrl());
                }



                bundle.putString("profesion",Datas.getmProfesion());
                bundle.putString("name",Datas.getmName());
                bundle.putInt("eventId",Datas.getEventId());
                bundle.putString("phone",Datas.getmPhoneNumber());
                bundle.putString("mId",Datas.getmId());
                fragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.slide_up,
                                R.animator.slide_down,
                                R.animator.slide_up,
                                R.animator.slide_down)
                        .replace(R.id.fragments, fragment)
                        .commit();
            }
        });


        return rootView;
    }

}
