package com.skywalker.android.apps.eventm.Fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skywalker.android.apps.eventm.Adapter.PagerViewAdapter;
import com.skywalker.android.apps.eventm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Pass extends Fragment {

    TextView Pass,notification,teamRequest;
    PagerViewAdapter pagerViewAdapter;
    ViewPager viewPager;
    public Pass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pass, container, false);

        Pass = rootView.findViewById(R.id.pass);
        notification = rootView.findViewById(R.id.notification);
        teamRequest = rootView.findViewById(R.id.Team_request);
        viewPager = rootView.findViewById(R.id.frag_containr);

        pagerViewAdapter = new PagerViewAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(pagerViewAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {
                onChangeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        teamRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });






        return rootView;
    }
    public void onChangeTab(int position){

        if(position == 1){
            teamRequest.setTextSize(30);
            Pass.setTextSize(27);
            notification.setTextSize(27);

        }
        if(position == 0){
            Pass.setTextSize(30);
            teamRequest.setTextSize(27);
            notification.setTextSize(27);
        }
        if(position == 2){
            notification.setTextSize(30);
            Pass.setTextSize(27);
            teamRequest.setTextSize(27);

        }

    }
}
