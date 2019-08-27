package com.skywalker.android.apps.eventm.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.skywalker.android.apps.eventm.Fragments.EntryPass;
import com.skywalker.android.apps.eventm.Fragments.Notification;
import com.skywalker.android.apps.eventm.Fragments.Pass;
import com.skywalker.android.apps.eventm.Fragments.TeamRequest;

public class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new EntryPass();
                break;
            case 1:
                fragment = new TeamRequest();
                break;
            case 2:
                fragment = new Notification();
                break;


        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

