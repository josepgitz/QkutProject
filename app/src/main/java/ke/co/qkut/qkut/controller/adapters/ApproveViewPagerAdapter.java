package ke.co.qkut.qkut.controller.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import ke.co.qkut.qkut.views.fragments.FragmentApprove;

public class ApproveViewPagerAdapter extends FragmentStatePagerAdapter {
    String [] titles= new String[]{"Approve","Disapprove "};
    FragmentApprove fragmentApprove= new FragmentApprove();
    int ItemCount;

    public int getItemCount() {
        return ItemCount;
    }

    public void setItemCount(int itemCount) {
        ItemCount = itemCount;
    }

    public ApproveViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        FragmentApprove fragmentApprove= new FragmentApprove();
        Bundle bundle= new Bundle();
        bundle.putInt("count",getItemCount());
        fragmentApprove.setArguments(bundle);
        return fragmentApprove;
    }


}
