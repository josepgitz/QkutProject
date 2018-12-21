package ke.co.qkut.qkut.controller.controlUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ke.co.qkut.qkut.R;

public class QkutFragmentManager {
    private static AppCompatActivity appCompatActivity;
    public static void fragmentTransaction(Fragment fragment, AppCompatActivity CompatActivity){
        appCompatActivity=CompatActivity;
        FragmentManager fragmentManager= appCompatActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();
    }
}
