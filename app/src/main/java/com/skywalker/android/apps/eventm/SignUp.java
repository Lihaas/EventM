package com.skywalker.android.apps.eventm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
TextView go_on_loginPage,go_on_PhoneAuth;
EditText name,password,re_password;
Button signup;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        go_on_loginPage = findViewById(R.id.go_on_login2);
        go_on_PhoneAuth = findViewById(R.id.go_on_phoneAuth);
        name = findViewById(R.id.signup_name);
        password = findViewById(R.id.signup_password);
        re_password = findViewById(R.id.signUp_re_password);
        signup = findViewById(R.id.bSignups);
        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailSign = name.getText().toString();
                String passSign = password.getText().toString();
                String passAgain = re_password.getText().toString();


                if(TextUtils.isEmpty(mailSign)){
                    name.setError("Enter proper mail");
                    name.setEnabled(true);
                //    Toast.makeText(SignUp.this, "Enter proper mail", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(passSign) || passSign.length() < 6){
                    password.setError("Enter proper password");
                    password.setEnabled(true);
                  //  Toast.makeText(SignUp.this, "Enter proper passowrd", Toast.LENGTH_SHORT).show();
                }
                if(!passAgain.equals(passSign)){

                    re_password.setError("Password didn't match");
                    re_password.setEnabled(true);

                }
                else{


                    firebaseAuth.createUserWithEmailAndPassword(mailSign,passSign)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //    Toast.makeText(signup.this, "SignUp", Toast.LENGTH_SHORT).show();
                                        //   startActivity(new Intent(signup.this,home.class));

                                        sendVerificationEmail();
                                    }else{
                                           Toast.makeText(SignUp.this, "Regester fail", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }





            }
        });


    }


    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUp.this, LogIn.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Toast.makeText(SignUp.this, "Email not send", Toast.LENGTH_SHORT).show();
                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

}
