package ke.co.qkut.qkut.controller.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ke.co.qkut.qkut.views.fragments.AlertsFragment;
import ke.co.qkut.qkut.views.fragments.HomeFragment;
import ke.co.qkut.qkut.views.fragments.LoginFragment;
import ke.co.qkut.qkut.views.fragments.ScheduleFragment;
import ke.co.qkut.qkut.views.fragments.SignUpFragment;

public class PageAdapter extends FragmentStatePagerAdapter {
    private  String [] PAGES_NAME;
    AppCompatActivity appCompatActivity;
    ViewPager viewPager;
    boolean loggedin=false;
    SignUpFragment signUpFragment= new SignUpFragment();
    LoginFragment loginFragment= new LoginFragment();
    HomeFragment homeFragment= new HomeFragment();

    public PageAdapter(ViewPager viewPager, AppCompatActivity appCompatActivity, String[] PAGES_NAME) {
        super(appCompatActivity.getSupportFragmentManager());
        this.appCompatActivity=appCompatActivity;
        this.PAGES_NAME=PAGES_NAME;
        this.viewPager=viewPager;

    }

    public String[] getPAGES_NAME() {
        return PAGES_NAME;
    }

    public void setPAGES_NAME(String[] PAGES_NAME) {
        this.PAGES_NAME = PAGES_NAME;
    }

    public boolean isLoggedIn() {
        return loggedin;
    }

    public void setLoggedIn(boolean loggedin) {
        this.loggedin = loggedin;
    }

    @Override
    public Fragment getItem(int position) {
        if (!loggedin){
            switch (position){
                case 2:{

                    return signUpFragment;
                } case 1:{

                    return loginFragment;

                }case 0:{
                    return  homeFragment;
                }
            }
            return  new LoginFragment();
        }else{
            switch (position){
                case 2:{
                    AlertsFragment alertsFragment= new AlertsFragment();
                    return alertsFragment;
                } case 1:{
                    ScheduleFragment scheduleFragment= new ScheduleFragment();
                    return scheduleFragment;

                }case 0:{
                    HomeFragment homeFragment= new HomeFragment();
                    return  homeFragment;
                }
            }
            return  new LoginFragment();
        }

    }

    @Override
    public int getCount() {
        return PAGES_NAME.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PAGES_NAME[position];
    }
}
