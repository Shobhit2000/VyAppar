package com.example.gov.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gov.Fragment.BlankFragment;
import com.example.gov.Fragment.BlankFragment2;
import com.example.gov.Fragment.SocialMediaFragment;
import com.example.gov.Fragment.listFragment;

public class viewPageAdapter2 extends FragmentPagerAdapter {

    private Fragment[] childFragment;

    public viewPageAdapter2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        childFragment = new Fragment[] {
                new listFragment(), //0
                new SocialMediaFragment(), //1

        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return childFragment[position];
    }

    @Override
    public int getCount() {
        return childFragment.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title ;

        if(position==0)
        {
            title="List";
        }else
        {
            title="Social Media";
        }

        return (CharSequence) title;


    }
}
