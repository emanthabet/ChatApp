package com.route.chatapp.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.route.chatapp.ChatroomList;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>fragments;
    private ArrayList<String>titles;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        this.fragments=new ArrayList<>();
        this.titles=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
      return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment,String title)
    {fragments.add(fragment);
     titles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
