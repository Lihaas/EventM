package com.skywalker.android.apps.eventm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Screen extends AppCompatActivity {
    EditText txt_password_recovery;
    CallbackManager callbackManager;
    private FirebaseAuth myAuth;
    Button button_Next;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__screen);

        txt_password_recovery = findViewById(R.id.txt_of_recovery);
        button_Next = findViewById(R.id.button_next_recovery);


        myAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value = txt_password_recovery.getText().toString().trim();
                }catch (Exception e){}

                if(TextUtils.isEmpty(value)){
                    txt_password_recovery.setError("Enter Valid Email or PhoneNo");
                    txt_password_recovery.setEnabled(true);
                }

                for(int i = 0;i<value.length();i++){
                    if(value.charAt(i) == '@'){
                        mailForgot(value);
                        return;
                    }

                }
                phoneNoForgot(value);

            }
        });


    }



    //Forgot Mail
    private void mailForgot(String mail){

        myAuth.getInstance().sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Forgot_Screen.this, "Reset Passowrd Sent to Mail", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Forgot_Screen.this,LogIn.class));

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Forgot_Screen.this, "E-Mail not found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Forgot through phoneNo
    private void phoneNoForgot(String phoneno){

        if(phoneno.length() >10 || phoneno.length() <10){
            txt_password_recovery.setError("Enter correct number");
            txt_password_recovery.setEnabled(true);
        }

        else{
        String PhoneNo = "+" + "91" + phoneno;

        Intent i = new Intent(Forgot_Screen.this, Otp_Screen.class);
        i.putExtra("Number", PhoneNo);

        startActivity(i); }
    }



}
