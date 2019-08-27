package com.skywalker.android.apps.eventm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class Splash extends AppCompatActivity {

    boolean moving;
    int lastDir;
    CircleImageView logo;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.profile_image3);


        spinLogo();





    }

    public void spinLogo(){




        if (!moving) {


            //Starting point of image in x axis
            float pivotx = logo.getWidth() / 1;

            //Starting point of image in y axis
            float pivoty = logo.getHeight() / 1;

            //Defining Animation

            Animation spin = new RotateAnimation(0, 360, pivotx, pivoty);

            //Set duration
            spin.setDuration(2500);

            //When set to true , the animation transformation is applied after the animation is over
            spin.setFillAfter(true);

            //Set Listener on Animation
            spin.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    //    txt.setVisibility(View.INVISIBLE);
                    moving = true;

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    try {

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            Intent i = new Intent(Splash.this, MainActivity.class);
                            startActivity(i);
                        }
                    }catch (Exception e){}
                    startActivity(new Intent(Splash.this,LogIn.class));
                    finish();


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //  lastDir = newDir;

            //Start Animation
            logo.startAnimation(spin);
        }









    }

}
