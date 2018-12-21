package ke.co.qkut.qkut.datastore;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class LocalDatabase {
    private static String AUTH_TOKEN="TOKEN";
    public  static String NOT_LOGGED_IN="NO_TOKEN";//"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjA1MjYwMTRjNGY3ODY0NDUyZDNlMmY1YjMwZTBhOWIxN2YwNjk0ZWIwZjUwYTMxNDkyOTc0YTQ0NTRkNjhlZWE4MmM5MjJhYjJmYmY5NmI2In0.eyJhdWQiOiIxIiwianRpIjoiMDUyNjAxNGM0Zjc4NjQ0NTJkM2UyZjViMzBlMGE5YjE3ZjA2OTRlYjBmNTBhMzE0OTI5NzRhNDQ1NGQ2OGVlYTgyYzkyMmFiMmZiZjk2YjYiLCJpYXQiOjE1NDIwMzIwNDgsIm5iZiI6MTU0MjAzMjA0OCwiZXhwIjoxNTczNTY4MDQ4LCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.Omijr71eCsCCg2wsRE6cDMoEj9j3rIQbzCz7Ck_YLGidmyajWv-1vdGOz1VRRNIRPrrbMtQh6Auc1uylvU3DOVRIlp93k5BBWS9A2uBVKJDxORnl5Ly28BlaYOSc3pTuTSjcDsO2cYK7zGwRtrlOAiyVC9T6mvLUVSGIpCkKkfohemurVejv74vYKDGmT46FcXmZpZ0XMoleAV4WQJE87fw_VJKAu7KBXZdow03wZRQff3ppKAD0itg0S9QFDtEDczNZh6gZgQx5Ij4UhFLNhWb3_qbQEzJ6X1oUkijIhqht5oQhGA1ehDcEUt9F2fq8pkl8O5mlYNGqXHM-9mxTimfRtc-xvDbFli3b-0ifEKJnh6rAV6H0epkYwIfWzb0h_vZaVZ4PF7r5-CIT58qQhvCsNb9ao749OMTbxslmcqd8FOnMlLLyEXB4Tx_St2YHdZ8ZCUdmKc_ugKG9JrtmPkhZIjotkLfeMyKR-pcy8XWte1pTf4refM8_IYmn22tTS66jyY01L1QJxOUVPygReet7RPov7fmueshkO7pJzOqm0Ok-drFXTuWtSyteTIgvGKU433TL1yb7dQ5ZyfTH6mTAKyLLm6eaxVntvWRJN4KBgx1QKxD9B_AZuvRp07hnr1EIl0NcwSogTb5FD9d87gSKjDYJ5c4HYQ33HqvURqM";
    public static  String FIREBASE_AUTH_TOKEN="FIREBASE_TOKEN";
    public  static  String PREFERENCE_NAME="AUTH_PREFERENCE";
    public static  String FIREBASE_NO_TOKEN="FIREBASE_TOKEN";
    public static String USER_LTD= "0.0";
    public static String USER_LNG="0.0";
    public static double lat=0.0;
    public static double lng=0.0;;

    static SharedPreferences sharedPreferences;
    public LocalDatabase(SharedPreferences preferences){
        this.sharedPreferences=preferences;
    }

    public  static  String getToken(AppCompatActivity appCompatActivity){
        SharedPreferences sharedPreferences=appCompatActivity.getPreferences(Context.MODE_WORLD_READABLE) ;
        return sharedPreferences.getString(AUTH_TOKEN,NOT_LOGGED_IN);
    }
    public  static  void   setToken(AppCompatActivity appCompatActivity,String token ){
        SharedPreferences sharedPreferences=appCompatActivity.getPreferences(Context.MODE_WORLD_READABLE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AUTH_TOKEN,token);
        editor.apply();
    }
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(AUTH_TOKEN,NOT_LOGGED_IN);
    }
    public static void clearToken(AppCompatActivity appCompatActivity){

        SharedPreferences sharedPreferences=appCompatActivity.getPreferences(MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AUTH_TOKEN,NOT_LOGGED_IN);
        editor.apply();


    }
    public static void saveFirebaseToken(Context context, String token){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(FIREBASE_AUTH_TOKEN,token);
        editor.apply();
    }
    public  static  String getFireBaseToken(Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(FIREBASE_AUTH_TOKEN,FIREBASE_AUTH_TOKEN);

    }


    //-------------------------------------------------------------------------------------------------------------------------------------------
      //function to set user current location
    //-------------------------------------------------------------------------------------------------------------------------------------------
    public  static  void   setCurrentLocation(Double lat, Double lng ){
        //SharedPreferences sharedPreferences1=appCompatActivity. getSharedPreferences("longitude", MODE_PRIVATE) ;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_LTD, String.valueOf(lat));
        editor.putString(USER_LNG, String.valueOf(lng));
        editor.apply();
    }
//get userLocation
    public  static  Double getLongitude(Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sharedPreferences.getString(USER_LNG,USER_LNG));

    }

    //get userLocation
    public  static  Double getLatitude(Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(sharedPreferences.getString(USER_LNG,USER_LNG));

    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    // function to get user latitude
    //-------------------------------------------------------------------------------------------------------------------------------------------

    public  static  Double getltd(){
      //  Log.e("LAT",sharedPreferences.getString(USER_LTD, String.valueOf(0.0)));
        return    Double.parseDouble(sharedPreferences.getString(USER_LTD, String.valueOf(0.0)));
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    // function to get user longitude
    //-------------------------------------------------------------------------------------------------------------------------------------------
    public  static  Double getlng(){
        Log.e("LONG",sharedPreferences.getString(USER_LNG, String.valueOf(0.0)));
        return    Double.parseDouble(sharedPreferences.getString(USER_LNG, String.valueOf(0.0)));


    }
}
