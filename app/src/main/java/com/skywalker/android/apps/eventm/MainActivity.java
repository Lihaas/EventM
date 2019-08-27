package com.skywalker.android.apps.eventm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skywalker.android.apps.eventm.Fragments.Home;
import com.skywalker.android.apps.eventm.Fragments.Pass;
import com.skywalker.android.apps.eventm.Fragments.Profile;
import com.skywalker.android.apps.eventm.Fragments.Search;
import com.skywalker.android.apps.eventm.Fragments.TeamRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //Used to load fragment
    private boolean loadFarg(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Home();
                    break;
                case R.id.navigation_search:
                    fragment = new Search();
                    break;

                case R.id.navigation_pass:
                    fragment = new Pass();
                    break;

                case R.id.navigation_profile:
                    fragment = new Profile();
                    break;

            }
            return loadFarg(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
          int  id = getIntent().getIntExtra("passDetial",410);

          if(id == 2){
          loadFarg(new Pass());}
          else {
              loadFarg(new Search());
          }
        }
        else {
            loadFarg(new Home());

        }


    }


}
