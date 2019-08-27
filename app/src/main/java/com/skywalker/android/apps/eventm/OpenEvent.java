package com.skywalker.android.apps.eventm;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skywalker.android.apps.eventm.Fragments.FullDetail_Fragment;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenEvent extends DialogFragment{
    ImageView imgCome;
    String imgg,team_Streangth,short_detail,Eventss,location;
    TextView prizes,how_much_in_team,shortDetail,EventName,moreDetail;
    int prize,enteryFee;

    public OpenEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_open_event, container, false);

        imgCome = rootView.findViewById(R.id.image_selected);
        prizes = rootView.findViewById(R.id.prize);
        how_much_in_team = rootView.findViewById(R.id.how_much_in_team);
        shortDetail = rootView.findViewById(R.id.shortDetail);
        EventName = rootView.findViewById(R.id.event_name);
        moreDetail = rootView.findViewById(R.id.moreDetail);





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
      //  imgCome.setBackgroundResource(imgg);

        moreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("img",imgg);
                bundle.putInt("prize",prize);
                bundle.putString("No_of_parti",team_Streangth);
                bundle.putString("detail",short_detail);
                bundle.putString("EventName",Eventss);
                bundle.putInt("EntryFee",enteryFee);
                bundle.putString("Location",location);

                FullDetail_Fragment fragment = new FullDetail_Fragment();
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



        Picasso.get().load(imgg).into(imgCome);
        String prizee = "Prize - "+ prize +"Rs";
        prizes.setText(prizee);
        String s = team_Streangth + " participant only";
        how_much_in_team.setText(s);
        shortDetail.setText(short_detail);
        EventName.setText(Eventss);

        return rootView;



    }

}
