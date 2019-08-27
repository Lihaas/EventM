package com.skywalker.android.apps.eventm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Phone_Auth extends AppCompatActivity {

    EditText phoneNO;
    Button phoneAuth;
    TextView go_on_logIn,go_on_mailAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__auth);

        phoneAuth = findViewById(R.id.next_for_otp);
        phoneNO = findViewById(R.id.txt_phoneNumber);
        go_on_logIn = findViewById(R.id.go_on_logIn);
        go_on_mailAuth = findViewById(R.id.go_on_emailPage);

        go_on_mailAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Phone_Auth.this,SignUp.class));
            }
        });

        go_on_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Phone_Auth.this,LogIn.class));
            }
        });




        phoneAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String number = phoneNO.getText().toString().trim();

                if(TextUtils.isEmpty(number)||number.length()<10||number.length()>10){
                    phoneNO.setError("Valid Number Required");
                    phoneNO.requestFocus();
                }
                else {
                    String PhoneNo = "+" + "91" + number;

                    Intent i = new Intent(Phone_Auth.this, Otp_Screen.class);
                    i.putExtra("Number", PhoneNo);

                    startActivity(i);
                }

            }
        });

    }
}
