package com.skywalker.android.apps.eventm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Email_Verification extends AppCompatActivity {
Button back;
TextView email_that_recover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email__verification);

        back = findViewById(R.id.back_button);
        email_that_recover = findViewById(R.id.email_for_recover);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Email_Verification.this,LogIn.class));
            }
        });

        try {
            String emailss = getIntent().getStringExtra("emails");
            email_that_recover.setText(emailss);
        }catch (Exception e){}

    }
}
