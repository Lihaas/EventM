package com.skywalker.android.apps.eventm.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skywalker.android.apps.eventm.Adapter.DanceAdapter;
import com.skywalker.android.apps.eventm.Adapter.TechAdapter;
import com.skywalker.android.apps.eventm.Model.DancePojo;
import com.skywalker.android.apps.eventm.Model.TechPojo;
import com.skywalker.android.apps.eventm.R;
import com.skywalker.android.apps.eventm.ViewPager.CircularViewPagerHandler;
import com.skywalker.android.apps.eventm.ViewPager.ViewPagerAdapter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    Timer timer;
    private ImageView[] dots;
    RecyclerView.Adapter adapters ;
    RecyclerView.Adapter adapters2 ;
    List<TechPojo> lists = new ArrayList<>();
    List<DancePojo>lists2 = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ProgressBar progressBar;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = rootView.findViewById(R.id.viewPager);
        sliderDotspanel = rootView.findViewById(R.id.SliderDots);
        progressBar = rootView.findViewById(R.id.spin_kit);


        CheckInternet();





        adapters = new TechAdapter(lists,getActivity());
        database = FirebaseDatabase.getInstance();
        databaseReference =database.getReference().child("Events").child("TechEvent");
        databaseReference2 =database.getReference().child("Events").child("DanceEvent");
        recyclerView = rootView.findViewById(R.id.recycle1);
        recyclerView2 = rootView.findViewById(R.id.recycle2);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));


        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView2.setVisibility(View.INVISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lists.removeAll(lists);
                //All data change in database notify here
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    //Add data to pojo class
                    TechPojo Datas = dataSnapshot1.getValue(TechPojo.class);
                    lists.add(Datas);

                }
                //Saying to adapter on chnage on data
                adapters.notifyDataSetChanged();


            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        adapters = new TechAdapter(lists, getApplicationContext());
        recyclerView.setAdapter(adapters);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                lists2.removeAll(lists2);
                //All data change in database notify here
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    //Add data to pojo class
                    DancePojo Datas = dataSnapshot1.getValue(DancePojo.class);
                    lists2.add(Datas);

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                }
                //Saying to adapter on chnage on data

                adapters2.notifyDataSetChanged();


                CheckInternet();
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                CheckInternet();
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        adapters2 = new DanceAdapter(lists2, getApplicationContext());
        recyclerView2.setAdapter(adapters2);



        /*lists.add(new Pojo(R.drawable.aaaaaaaa));
        lists.add(new Pojo(R.drawable.aaaaaaaa));
        lists.add(new Pojo(R.drawable.aaaaaaaa));
        lists.add(new Pojo(R.drawable.aaaaaaaa));
        lists.add(new Pojo(R.drawable.bbbbbbbb));
        lists.add(new Pojo(R.drawable.bbbbbbbb));
        lists.add(new Pojo(R.drawable.ccccccccccc));


        adapters = new TechAdapter(lists,getActivity());
        recyclerView.setAdapter(adapters);
        adapters2 = new DanceAdapter(lists,getActivity());
        recyclerView2.setAdapter(adapters2);*/


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setOnPageChangeListener(new CircularViewPagerHandler(viewPager));
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();

        AutoSlidingPager();
        SetUpDots();

        //Setup dots change with image
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot)); }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return rootView;
    }

    public void AutoSlidingPager(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%dotscount);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 4000, 4000);


    }
    public void SetUpDots(){
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
    }


    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
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

    public void CheckInternet(){
        boolean internet = isInternetAvailable();
        if(internet){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Please check internet!!");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().finish();
                    System.exit(0);
                }


            });
            builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                            CheckInternet();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }


    }


    @Override
    public void onResume() {
        super.onResume();
        CheckInternet();
    }





}
