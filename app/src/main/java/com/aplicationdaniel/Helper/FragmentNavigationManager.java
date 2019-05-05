package com.aplicationdaniel.Helper;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;

import com.aplicationdaniel.BuildConfig;
import com.aplicationdaniel.Fragments.FragmentContent;
import com.aplicationdaniel.HomeActivity;
import com.aplicationdaniel.Intervace.NavigationManager;
import com.aplicationdaniel.R;

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private HomeActivity homeActivity;

    public static FragmentNavigationManager getmInstance(HomeActivity homeActivity)
    {
        if(mInstance==null)
            mInstance = new FragmentNavigationManager();
        mInstance.configure(homeActivity);
        return mInstance;
    }

    private void configure(HomeActivity homeActivity) {
        homeActivity = homeActivity;
        mFragmentManager = homeActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment (String title) {

        showFragment(FragmentContent.newInstance(title),false);

    }



    private void showFragment(Fragment fragmentContent, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container,fragmentContent);
        ft.addToBackStack(null);
        if (b || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();
    }

}
