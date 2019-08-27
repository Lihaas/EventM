package com.skywalker.android.apps.eventm.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalker.android.apps.eventm.Fragments.Qr;
import com.skywalker.android.apps.eventm.Model.PassCreator;
import com.skywalker.android.apps.eventm.Model.RequestReciver;
import com.skywalker.android.apps.eventm.OpenEvent;
import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.PassViewHOlder> {

    List<PassCreator> data;
    Context context;

    public PassAdapter( List<PassCreator> data, Context context){

        this.context = context;
        this.data = data;

    }


    @NonNull
    @Override
    public PassViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.demo_pass,parent,false);

        return new PassViewHOlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PassViewHOlder holder, int position) {
                PassCreator ps = data.get(position);

                holder.nameOfTeam.setText(ps.getmTeamName());
                holder.nameOfEvent.setText(ps.getmEvent());
        Picasso.get().load(ps.getmImageUrl()).into(holder.crImg);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PassViewHOlder extends RecyclerView.ViewHolder {

        CircleImageView crImg;
        TextView nameOfEvent;
        TextView nameOfTeam;
        Button getPass;


        public PassViewHOlder(@NonNull View itemView) {
            super(itemView);

            crImg = itemView.findViewById(R.id.eventpicsss);
            nameOfEvent = itemView.findViewById(R.id.eventName2sss);
            nameOfTeam = itemView.findViewById(R.id.teamNamessss);

            getPass = itemView.findViewById(R.id.getYourPas);



            getPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PassCreator passGenerate = data.get(getAdapterPosition());

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Validity",passGenerate.getmValid());
                    bundle.putString("EventName",passGenerate.getmEvent());
                    bundle.putString("TeamName",passGenerate.getmTeamName());


                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new Qr();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.slide_up,
                                    R.animator.slide_down,
                                    R.animator.slide_up,
                                    R.animator.slide_down)
                            .replace(R.id.fragments, myFragment)
                            .addToBackStack(null).commit();




                }
            });


        }
    }
}
