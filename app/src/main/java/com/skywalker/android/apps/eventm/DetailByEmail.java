package com.skywalker.android.apps.eventm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skywalker.android.apps.eventm.Model.DetailPojo;

import java.util.Random;

import es.dmoral.toasty.Toasty;

public class DetailByEmail extends AppCompatActivity {
EditText name,phoneNO,profesion;
Button letsStart;
String mName,mPhoneNo,mProfession;
    FirebaseDatabase database ;
    DatabaseReference mDataBase;
    ProgressBar pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_by_email);

        name = findViewById(R.id.nameOfUser);
        phoneNO = findViewById(R.id.PhoneNoOfUser);
        profesion = findViewById(R.id.profesionOfUser);
        letsStart = findViewById(R.id.letsStart);
        pr = findViewById(R.id.detailTakenProgress);


        database = FirebaseDatabase.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("User");






        letsStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mName = name.getText().toString();
                mPhoneNo = phoneNO.getText().toString();
                mProfession = profesion.getText().toString();

                if(TextUtils.isEmpty(mName)){
                    name.setError("Enter Your Name");
                    name.setEnabled(true);
                    return;
                }
                if(TextUtils.isEmpty(mPhoneNo) || mPhoneNo.length()!=10){
                    phoneNO.setError("Enter phone number");
                    phoneNO.setEnabled(true);
                    return;
                }
                if(TextUtils.isEmpty(mProfession)){
                    profesion.setError("Enter Your Profession");
                    profesion.setEnabled(true);
                    return;
                }

                else{

                    Random ran = new Random();
                    int eventId = ran.nextInt(9999);
                    pr.setVisibility(View.VISIBLE);
                    DetailPojo dp = new DetailPojo();
                    dp.setmName(mName);
                    dp.setEventId(eventId);
                    dp.setmPhoneNumber(mPhoneNo);
                    dp.setmProfesion(mProfession);
                    dp.setmId(currentuser);
                    dp.setmImageUrl("https://firebasestorage.googleapis.com/v0/b/eventm-2ced2.appspot.com/o/Profile_pic%2FproPic.JPG?alt=media&token=67aa3258-32ce-4615-ab65-464df8f7b1e3");
                    mDataBase.child(currentuser).setValue(dp)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toasty.success(DetailByEmail.this, "Welcome", Toast.LENGTH_SHORT, true).show();
                                    pr.setVisibility(View.GONE);
                                    startActivity(new Intent(DetailByEmail.this,MainActivity.class));
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailByEmail.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    pr.setVisibility(View.INVISIBLE);
                        }
                    });






                }




            }
        });

    }
}
