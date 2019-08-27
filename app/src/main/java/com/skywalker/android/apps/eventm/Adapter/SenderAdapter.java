package com.skywalker.android.apps.eventm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skywalker.android.apps.eventm.Model.RequestSender;
import com.skywalker.android.apps.eventm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SenderAdapter extends RecyclerView.Adapter<SenderAdapter.sendDataViewHolder> {

    List<RequestSender> data;
    Context context;

    public SenderAdapter( List<RequestSender> data ,Context context){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public sendDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.demo_request_send,parent,false);
        return new sendDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull sendDataViewHolder holder, int position) {
                RequestSender rq = data.get(position);
                try {
                    holder.teamName2.setText(rq.getmTeamName());
                    holder.eventName2.setText(rq.getmEventName());

                    Picasso.get().load(rq.getmImgName()).into(holder.cr);
                }catch (Exception e){

                }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class sendDataViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cr;
        TextView eventName2;
        TextView teamName2;




       public sendDataViewHolder(@NonNull View itemView) {
           super(itemView);

           cr = itemView.findViewById(R.id.eventpic);
           eventName2 = itemView.findViewById(R.id.eventName2);
           teamName2  = itemView.findViewById(R.id.teamNames);
           Button closing = itemView.findViewById(R.id.cancle);

           closing.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   RequestSender requestSenderData = data.get(getAdapterPosition());
                   String id = requestSenderData.getmId();
                   DatabaseReference mDatabase;
                   String senderId = requestSenderData.getmSendBy();
                   final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                   mDatabase = FirebaseDatabase.getInstance().getReference();



                   mDatabase.child("User").child(currentuser).child("Request").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {



                       }
                   });

                   mDatabase.child("User").child(senderId).child("RequestRecive").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {



                       }
                   });



               }
           });

       }
   }
}
