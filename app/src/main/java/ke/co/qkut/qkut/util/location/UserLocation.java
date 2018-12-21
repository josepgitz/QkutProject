package ke.co.qkut.qkut.util.location;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.views.activities.MainActivity;

public class UserLocation implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private Context context;
    LocationManager locationManager;
    static  List<UserLocationUpdated> userLocationUpdated= new ArrayList<>();
    public UserLocation(final Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

requestLocationNow();

    }

    public static List<UserLocationUpdated> getUserLocationUpdateds() {
        return userLocationUpdated;
    }

    public static void setUserLocationUpdated(UserLocationUpdated userLocationUpdateds) {

        if (userLocationUpdated.size() == 0) {
            userLocationUpdated.add(userLocationUpdateds);
        }else{
            for (UserLocationUpdated userLocation:userLocationUpdated) {
                if(!userLocation.getClass().equals(userLocationUpdateds.getClass())){
                    userLocationUpdated.add(userLocationUpdateds);
                }
            }
        }



    }

    public void requestLocationNow(){
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions((AppCompatActivity)(context), new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION },
                MY_PERMISSIONS_REQUEST_LOCATION);
    } else {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

}
    @Override
    public void onLocationChanged(Location location) {
        Coordinate coordinate= new Coordinate();

        for (UserLocationUpdated userlocation:userLocationUpdated) {
            coordinate.setLatitude(location.getLatitude());
            coordinate.setLongitude(location.getLongitude());
            userlocation.userLocationChanged(coordinate);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
