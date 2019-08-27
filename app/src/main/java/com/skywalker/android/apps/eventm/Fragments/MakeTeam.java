package com.skywalker.android.apps.eventm.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skywalker.android.apps.eventm.Model.DetailPojo;
import com.skywalker.android.apps.eventm.Model.RequestSender;
import com.skywalker.android.apps.eventm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeTeam extends Fragment {

    TextView meventName;
    String nameOfEvnet,ImageUrl,id;
    EditText LeaderName,TeamName,MateName;
    Button bMakeTeam;
    FirebaseDatabase database;
    DatabaseReference databaseReference,getDatabaseReference,againRefrenceForUid;
    DetailPojo Datas;
    String UID,ok;
    RequestSender user;
    int setData = 1;

    String txtTeamName,txtTeamLeader,txtMateId;
    ProgressBar pr;


    public MakeTeam() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_make_team, container, false);
        LeaderName = rootView.findViewById(R.id.leaderName);
        TeamName = rootView.findViewById(R.id.Team_name);
        MateName = rootView.findViewById(R.id.mate_Id);
        bMakeTeam = rootView.findViewById(R.id.bMakeTeam);
        meventName = rootView.findViewById(R.id.event_names);
        pr = rootView.findViewById(R.id.makeTeamProgress);

        //Finding current User and Initialize firebase
        database = FirebaseDatabase.getInstance();
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference =database.getReference().child("User").child(currentuser);


        try {


            Bundle bundle = this.getArguments();
            nameOfEvnet = bundle.getString("EventName");
            ImageUrl = bundle.getString("ImageUrl");
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        meventName.setText("EventName - " + nameOfEvnet);

        bMakeTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTeamName = TeamName.getText().toString();
                txtMateId = MateName.getText().toString();

                if(TextUtils.isEmpty(txtTeamName)){
                    TeamName.setError("Enter Valid Team Name");
                    TeamName.setEnabled(true);

                }
                if(TextUtils.isEmpty(txtMateId)){
                    MateName.setError("Enter Valid Team Name");
                    MateName.setEnabled(true);

                }
                else{


                    pr.setVisibility(View.VISIBLE);
                    //Collecting info for sender
                    getDatabaseReference = database.getReference().child("User").child(currentuser).child("Request");
                    RequestSender tm = new RequestSender();
                    try {

                        id = getDatabaseReference.push().getKey();
                    }catch (Exception e){}
                    tm.setmId(id);
                    sendToResiver(txtMateId);



                    tm.setmTeamName(txtTeamName);
                    tm.setmEventName(nameOfEvnet);
                    tm.setmImgName(ImageUrl);
                    tm.setmSendBy(UID);
                    
                    //Giving Unique id to the Request that send



// Sending details in sender's database
                    getDatabaseReference.child(id).setValue(tm)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {





                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });






                }




                 }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Datas = dataSnapshot.getValue(DetailPojo.class);
                LeaderName.setText(Datas.getmName());
            }
                @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





        return rootView;

    }

    private void sendToResiver(String mateId) {
       int valuee = Integer.parseInt(mateId);

      searchPerson(valuee);
      //  Toast.makeText(getActivity(), UID, Toast.LENGTH_SHORT).show();




    }


    public void searchPerson(int l){

        Query query= FirebaseDatabase.getInstance().getReference("User").orderByChild("eventId")
                .equalTo(l);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    user=snapshot.getValue(RequestSender.class);
                  //  txt.setText(user.getId());
                    UID = user.getmId();
                    sendRequest(UID);
                  //  Toast.makeText(getActivity(), UID, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void sendRequest(String i){

        if(setData == 1) {

            setData +=1 ;
            String reciverId = i;
            final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            getDatabaseReference = database.getReference().child("User").child(reciverId).child("RequestRecive");
            RequestSender tm = new RequestSender();
            tm.setmTeamName(txtTeamName);
            tm.setmEventName(nameOfEvnet);
            tm.setmImgName(ImageUrl);
            tm.setmSendBy(currentuser);

            //Giving Unique id to the Request that send

            tm.setmId(id);
            getDatabaseReference.child(id).setValue(tm)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            getActivity().finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            againRefrenceForUid = database.getReference().child("User").child(currentuser).child("Request");

           againRefrenceForUid.child(id).child("mSendBy").setValue(UID)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {




                            Toast.makeText(getActivity(), UID, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });







        }







    }


}
