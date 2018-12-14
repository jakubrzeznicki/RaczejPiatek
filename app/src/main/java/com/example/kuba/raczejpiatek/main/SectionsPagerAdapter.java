package com.example.kuba.raczejpiatek.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                RequestsFragment requestsFragment = new RequestsFragment();
            return requestsFragment;

            case 1:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            case 2:
                FindFriendFragment findFragment = new FindFriendFragment();
                return findFragment;




            default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Zaproszenia";

            case 1:
                return "Przyjaciele";

            case 2:
                return "Szukaj znajomych";


            default:
                return null;
        }
    }
}
