package com.skywalker.android.apps.eventm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skywalker.android.apps.eventm.Model.PassCreator;
import com.skywalker.android.apps.eventm.Model.RequestReciver;
import com.skywalker.android.apps.eventm.Model.RequestSender;
import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReciverAdapter extends RecyclerView.Adapter<ReciverAdapter.ReciverViewHolder> {

    String id;
    List<RequestReciver> data;
    Context context;

    public ReciverAdapter(  List<RequestReciver> data , Context context){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ReciverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.demo_request_reciver,parent,false);
        return new ReciverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReciverViewHolder holder, int position) {
                        RequestReciver rq = data.get(position);

                        holder.eventName2.setText(rq.getmEventName());
                        holder.teamName2.setText(rq.getmTeamName());
                        Picasso.get().load(rq.getmImgName()).into(holder.cr);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cr;
        TextView eventName2;
        TextView teamName2;


        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);


            cr = itemView.findViewById(R.id.eventpicss);
            eventName2 = itemView.findViewById(R.id.eventName2ss);
            teamName2  = itemView.findViewById(R.id.teamNamesss);
            final Button cancle = itemView.findViewById(R.id.deletRequest);
            Button accept = itemView.findViewById(R.id.acceptRequest);

            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestReciver requestSenderData = data.get(getAdapterPosition());
                    String id = requestSenderData.getmId();
                    String senderId = requestSenderData.getmSendBy();
                    DatabaseReference mDatabase;
                    final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference();



                    mDatabase.child("User").child(currentuser).child("RequestRecive").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {



                        }
                    });

                    mDatabase.child("User").child(senderId).child("Request").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {



                        }
                    });



                }
            });


            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RequestReciver requestSenderData = data.get(getAdapterPosition());
                    String senderId = requestSenderData.getmSendBy();
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    try {
                        id = mDatabase.push().getKey();
                    }catch (Exception e){}

                    PassCreator ps = new PassCreator(requestSenderData.getmEventName(),
                            requestSenderData.getmTeamName(),
                            true,id,
                            requestSenderData.getmImgName()
                    );

                    mDatabase.child("User").child(senderId).child("Pass").child(id).setValue(ps).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            cancle.callOnClick();

                        }
                    });





                }
            });





        }
    }
}
