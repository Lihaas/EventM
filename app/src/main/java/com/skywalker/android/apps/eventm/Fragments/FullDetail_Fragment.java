package com.skywalker.android.apps.eventm.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullDetail_Fragment extends Fragment {

    TextView eventName,prizedetail,teamMemberDetail,fullDetail,enteryFees,myList,MakeTeam,Share;
    Button payNow;
    String imgg,team_Streangth,short_detail,Eventss,location;
    int prize,enteryFee;
    ImageView eventImage;

    public FullDetail_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_full_detail, container, false);

        //Finding ids
        eventName = rootView.findViewById(R.id.event_name_detial);
        prizedetail = rootView.findViewById(R.id.prize_detail);
        teamMemberDetail = rootView.findViewById(R.id.how_much_in_team_detial);
        fullDetail = rootView.findViewById(R.id.FullDetail);
        enteryFees = rootView.findViewById(R.id.rates);
        myList = rootView.findViewById(R.id.my_lsit);
        MakeTeam = rootView.findViewById(R.id.makeTeam);
        Share = rootView.findViewById(R.id.share);
        eventImage = rootView.findViewById(R.id.event_image);
        payNow = rootView.findViewById(R.id.payNow);


        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toasty.info(getActivity(), "Registration will open soon", Toast.LENGTH_LONG, true).show();
            }
        });

        MakeTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("EventName",Eventss);
                bundle.putString("ImageUrl",imgg);

                MakeTeam fragment = new MakeTeam();
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


        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey make a team with me at EventM and win Prizes: https://play.google.com/store/apps/developer?id=SkyWalker");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        try {


            Bundle bundle = this.getArguments();
            imgg = bundle.getString("img");
            prize = bundle.getInt("prize");
            team_Streangth = bundle.getString("No_of_parti");
            short_detail = bundle.getString("detail");
            Eventss = bundle.getString("EventName");
            location = bundle.getString("Location");
            enteryFee = bundle.getInt("EntryFee");
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Picasso.get().load(imgg).into(eventImage);
        eventName.setText(Eventss);
        prizedetail.setText("Prize - "+prize);
        teamMemberDetail.setText(team_Streangth+"participant only");
        fullDetail.setText(short_detail);
        enteryFees.setText(""+enteryFee+"Rs");









        return rootView;
    }

}
