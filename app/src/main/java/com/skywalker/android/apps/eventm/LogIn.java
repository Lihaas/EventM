package com.skywalker.android.apps.eventm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.InetAddress;

import es.dmoral.toasty.Toasty;

public class LogIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    CallbackManager mCallbackManager;
    Button fbLogin,loginButton;
    EditText getTxtEmail,getTxtPassword;
    TextView passwordRecovery;
    LinearLayout signUp_Opener;
    String email;
    ProgressBar progressBar;
    LinearLayout layout1;
    RelativeLayout relative1;


    @Override
    public void onStart() {
        super.onStart();

        boolean network = isNetworkConnected();
        boolean internet = isInternetAvailable();

        if(!network){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Network not connected!!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   finish();
                   System.exit(0);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        // Check if user is signed in (non-null) and update UI accordingly.



        else if(internet){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please check internet!!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    System.exit(0);
                }


            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }


      else {  FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent i = new Intent(LogIn.this,MainActivity.class);
            startActivity(i);
        }}





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Find Ids
        fbLogin = findViewById(R.id.fb);
        loginButton = findViewById(R.id.loginbutton);
        getTxtEmail = findViewById(R.id.getTxtEmail);
        getTxtPassword = findViewById(R.id.getTxtPassword);
        passwordRecovery = findViewById(R.id.recoveryPasswordTxt);
        signUp_Opener = findViewById(R.id.SignupScreen);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        progressBar = findViewById(R.id.loginProgress);
        layout1 = findViewById(R.id.layoutts);
        relative1 = findViewById(R.id.relativeLayout1);

        layout1.setEnabled(true);
        relative1.setEnabled(true);

        //Set up login with email
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 email = getTxtEmail.getText().toString();
                String password = getTxtPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LogIn.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LogIn.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }

                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //   startActivity(new Intent(MainActivity.this,home.class));
                                        Toast.makeText(LogIn.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();

                                    } else {
                                        checkIfEmailVerified();
                                        // If sign in fails, display a message to the user.
                                        // Toast.makeText(MainActivity.this, "Check Password Or Email", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                } }
        });

        signUp_Opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,Phone_Auth.class));
            }
        });

        //Recover Password
        passwordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,Forgot_Screen.class));
            }
        });

        //Connecting to Facebook
        final LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toasty.success(LogIn.this, "Success", Toast.LENGTH_LONG, true).show();

                progressBar.setVisibility(View.VISIBLE);
                layout1.setEnabled(false);
                relative1.setEnabled(false);
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                layout1.setEnabled(true);
                relative1.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LogIn.this, "Cancle", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                layout1.setEnabled(true);
                relative1.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //Facebook
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                          //  Toast.makeText(LogIn.this, "Shi h sb", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                            startActivity(new Intent(LogIn.this,DetailByEmail.class));

                            finish();




                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogIn.this, "Galat id", Toast.LENGTH_SHORT).show();
                            layout1.setEnabled(true);
                            relative1.setEnabled(true);
                        }

                        // ...
                    }
                });
    }


    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.

            startActivity(new Intent(LogIn.this, DetailByEmail.class));
            Toast.makeText(LogIn.this, "Successfully logged in", Toast.LENGTH_SHORT).show();

            finish();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Intent i = new Intent(LogIn.this,Email_Verification.class);
            //startActivity(new Intent(LogIn.this,Email_Verification.class));
            i.putExtra("emails",email);

            startActivity(i);

           // FirebaseAuth.getInstance().signOut();
            //Toast.makeText(this, "Verify", Toast.LENGTH_SHORT).show();
            //restart this activity

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }




}
