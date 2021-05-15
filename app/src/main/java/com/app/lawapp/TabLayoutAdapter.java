package com.app.lawapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabLayoutAdapter extends FragmentPagerAdapter {


    private int totalTabs;
    private Context myContext;


    public TabLayoutAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm, totalTabs);

        this.myContext = context;
        this.totalTabs = totalTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                CriminalLawyerFragment criminalLawyerFragment = new CriminalLawyerFragment();
                return criminalLawyerFragment;
            case 1:

                DivorseLawyerFragment divorseLawyerFragment = new DivorseLawyerFragment();
                return divorseLawyerFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
