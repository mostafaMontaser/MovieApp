package udacity.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.lang.ref.WeakReference;

import udacity.movieapp.view.MainActivity;

/**
 * Created by Mostafa Montaser on 12/129/2016.
 */

public class NavigationManager {

    protected static NavigationManager baseNavigationManager;
    protected WeakReference<MainActivity> currentActivity;

    private NavigationManager() {
    }
    public static NavigationManager getInstance(MainActivity baseActivity) {
        baseNavigationManager = new NavigationManager();
        baseNavigationManager.setCurrentActivity(baseActivity);
        return baseNavigationManager;
    }

    public WeakReference<MainActivity> getCurrentActivity() {
        return currentActivity;
    }

    private void setCurrentActivity(MainActivity baseActivity) {
        currentActivity = new WeakReference<MainActivity>(baseActivity);
    }

    private void clearAllFragmentManagerStack(FragmentManager fm) {
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStackImmediate();
        }
    }

    public void setFirstLevelFragment(Fragment fragment, Bundle args) {
        String backStateName = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            clearAllFragmentManagerStack(fragmentManager);
            if (args != null) {
                fragment.setArguments(args);
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.contentFrame, fragment, backStateName);
            transaction.commit();
        }
    }

    public void popToBackStackFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        if (manager == null) {
            return;
        }

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.contentFrame, fragment, backStateName);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public void popFragment() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            manager.popBackStackImmediate();
        }

    }

    public void pushFragment(Fragment fragment, Bundle args) {

        String backStateName = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            if (args != null) {
                fragment.setArguments(args);
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.contentFrame, fragment, backStateName);
            transaction.addToBackStack(backStateName);
            transaction.commit();
        }
    }

    private FragmentManager getSupportFragmentManager() {
        MainActivity baseActivity = currentActivity.get();
        if (baseActivity != null) {
            return baseActivity.getSupportFragmentManager();
        }
        return null;
    }



    public Fragment findFragmentByTag(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            return fm.findFragmentByTag(tag);
        }
        return null;
    }

    public Fragment getCurrentFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null && fm.getBackStackEntryCount() > 0) {
            String tag = fm.getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            return getSupportFragmentManager().findFragmentByTag(tag);
        }
        return  null;
    }
}
