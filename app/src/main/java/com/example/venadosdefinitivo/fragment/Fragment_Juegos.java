package com.example.venadosdefinitivo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.venadosdefinitivo.R;


public class Fragment_Juegos extends Fragment {

    private static ViewPager mViewPager;
    public static TabLayout tabLayout;
    public static int items = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__juegos, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.container);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });

        return view;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment;
            switch (position)
            {
                case 0: fragment = new Fragment_Copa();
                    break;
                case 1: fragment = new Fragment_Ascenso();
                    break;
                default: return null;
            }
            return fragment;
        }

        @Override
        public int getCount() {

            return items;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0: return "Copa MX";
                case 1: return "Ascenso MX";
            }
            return null;
        }
    }
}
