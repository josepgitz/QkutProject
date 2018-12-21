package ke.co.qkut.qkut.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.controller.adapters.BusinessInfoAdapter;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.util.location.Coordinate;
import ke.co.qkut.qkut.util.location.UserLocation;
import ke.co.qkut.qkut.util.location.UserLocationUpdated;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.models.BusinessInfo;
import ke.co.qkut.qkut.views.dialogs.DialogListener;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;
import ke.co.qkut.qkut.views.dialogs.SmartPayInputScreenDialog;

public class HomeFragment extends Fragment implements UserLocationUpdated{
ListView businessList;
TextView nobusiness;
    LocalDatabase localDatabase;
ViewPager viewPager;
RelativeLayout loadingBusiness;
    SearchView searchView;
    FloatingActionButton fab;
    SharedPreferences preferences;
    Double lat,lng;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState( Bundle outState) {

        super.onSaveInstanceState(outState);
      //  outState.putSerializable("" ,);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.home_fragment_layout,container,false);
        loadingBusiness=view.findViewById(R.id.loadingBusiness);
        getBusinesses("");
        nobusiness=view.findViewById(R.id.nobusiness);
        businessList=view.findViewById(R.id.business_list_view);
        viewPager= getActivity().findViewById(R.id.pager);
        UserLocation.setUserLocationUpdated(this);
        preferences=getActivity().getApplicationContext().getSharedPreferences("Prefs",Context.MODE_PRIVATE);
        localDatabase=new LocalDatabase(preferences);
        return view;
    }


    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onStart() {
        getBusinesses("");
        super.onStart();
    }

    @Override
    public void onResume() {
        String tag = "room_fragment";
        HomeFragment homeFragment = (HomeFragment) getFragmentManager().findFragmentByTag(tag);
        //
        if(homeFragment == null)
            homeFragment = new HomeFragment();


        getFragmentManager().beginTransaction().replace(R.id.container,homeFragment,tag).addToBackStack(tag).commit();
        searchView= getActivity().findViewById(R.id.search);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.requestFocus();
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                viewPager.setCurrentItem(0);
                Coordinate coordinate = new Coordinate();
                userLocationChanged( coordinate );
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {

                //getBusinesses(query);
                return true;
            }
        });
        getBusinesses("");
        super.onResume();
    }
    //    @Override
//    public void onResume() {
//
//        searchView= getActivity().findViewById(R.id.search);
//        searchView.setQueryHint(getString(R.string.search_hint));
//        searchView.requestFocus();
//        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                viewPager.setCurrentItem(0);
//                Coordinate coordinate = new Coordinate();
//                userLocationChanged( coordinate );
//                return true;
//            }
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                //getBusinesses(query);
//                return true;
//            }
//        });
//        super.onResume();
//    }

    public void getBusinesses(String query) { //  Toast.makeText(getActivity(), coordinate.toString(), Toast.LENGTH_SHORT).show();
            try{
                JSONObject headers = new JSONObject();

                if(lat==0.0 && lng==0.0){
                    lat = localDatabase.getltd();
                    lng = localDatabase.getlng();
                }
                String url;
                Log.d("names", lat + " " + lng);
                String token;

                if ((token = localDatabase.getToken(((AppCompatActivity)getContext()))).equals(localDatabase.NOT_LOGGED_IN)) {
                    url = URL.GET_NEARBY_PLACES_FOR_GUEST+ "/" +lat+ "/" +lng+ "?q=" +query;
                    headers = GLobalHeaders.getGlobalHeaders((AppCompatActivity)getActivity());
                } else {
                    url = URL.GET_NEARBY_PLACES_FOR_NON_GUEST+"/"+lat+"/" +lng+ "?q="+query;
                    headers = GLobalHeaders.getGlobalHeaders((AppCompatActivity)getActivity());
                }
                Log.e("SEARCH_URL", url);
                Log.e("Token", token);
                NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
                    @Override
                    public void onErrorResult(IOException e) {
                        Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                        Log.e("Logged", remoteResponse.getMessage());
                    }

                    @Override
                    public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                        Log.e("Logged", remoteResponse.getMessage());
                        String resultStatus = "";
                        String resultData = "";
                        JSONObject response = new JSONObject();
                        try {
                            response = new JSONObject(remoteResponse.getMessage());
                            resultStatus = response.getString("status").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                        if (resultStatus.compareTo("ok") != 0) {

                            businessList.setVisibility(View.GONE);


                        } else { //good results
                            try {
                                JSONArray jsonBusinesses = response.getJSONArray("data");
                                populateBusinessList(jsonBusinesses);
                                Intent i = new Intent();


                            } catch (JSONException e) {
                                Log.d("JSON_ERROR", e.getMessage());
                                e.printStackTrace();

                            }
                        }


                    }

                    @Override
                    public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                        Log.e("Logged", remoteResponse.getMessage());
                    }

                    @Override
                    public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                        Log.e("Logged", remoteResponse.getMessage());
                    }

                    @Override
                    public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                        Log.e("Logged", remoteResponse.getMessage());
                    }
                });

            }catch (Exception e){

                e.printStackTrace();
            }
        }





    public void getBusinesses(String query,AppCompatActivity appCompatActivity) {

        JSONObject headers = new JSONObject();
        String url;
        String token;
        if ((token = localDatabase.getToken((appCompatActivity))).equals(localDatabase.NOT_LOGGED_IN)) {
            url = URL.GET_PLACES_FOR_GUEST + query;
        } else {

            url = URL.GET_PLACES_FOR_NON_GUEST + query;
            headers= GLobalHeaders.getGlobalHeaders(appCompatActivity);


        }
        Log.e("SEARCH_URL",url);
        Log.e("Token",token);
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                Toast.makeText(getContext(), "Please check your network", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged1",remoteResponse.getMessage());
            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged2",remoteResponse.getMessage());
                String resultStatus = "";
                String resultData = "";
                JSONObject response = new JSONObject();
                try {
                    response= new JSONObject(remoteResponse.getMessage());
                    resultStatus = response.getString("status").toString();
                } catch (JSONException e) {
                    e.printStackTrace();


                }
                if (resultStatus.compareTo("ok") != 0) {

                } else { //good results
                    try {
                        JSONArray jsonBusinesses = response.getJSONArray("data");
                        populateBusinessList(jsonBusinesses);

                    } catch (JSONException e) {
                        Log.d("JSON_ERROR", e.getMessage());
                        e.printStackTrace();

                    }
                }


            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged",remoteResponse.getMessage());
            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged",remoteResponse.getMessage());
            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged",remoteResponse.getMessage());
            }
        });


    }
   public void populateBusinessList(JSONArray jsonBusinesses) {
        List<BusinessInfo> businessInfoList= new ArrayList<>();
        for (int i = 0; i < jsonBusinesses.length(); i++) {
            try {
                Log.d("Valley", jsonBusinesses.getJSONObject(i).toString());
                businessInfoList.add(new BusinessInfo(jsonBusinesses.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       loadingBusiness.setVisibility(View.GONE);
        Log.e("Business Info",businessInfoList.size()+"");
        BusinessInfoAdapter businessInfoAdapter = new BusinessInfoAdapter((AppCompatActivity)getActivity(), businessInfoList,viewPager,view);
        businessInfoAdapter.setViewPager(viewPager);
        this.businessList.setAdapter(businessInfoAdapter);

    }
    public static void ShowBarCodeForm(final View view){
        FloatingActionButton fab = view.findViewById(R.id.fab);
        view.findViewById(R.id.barcode_scan_form).setVisibility(View.VISIBLE);
        fab.setImageResource(R.drawable.ic_arrow_back_white_24dp);
        View scanView = view.findViewById(R.id.barcode_scan_form);
        //BarCodeHandler barCodeHandler = new BarCodeHandler(view.getContext(), scanView);
    }



    @Override
    public void userLocationChanged(Coordinate coordinate ) {
         Log.d("Cordinates",coordinate.toString().toString());
      lat=coordinate.getLatitude();
        lng=coordinate.getLongitude();
        if (lat!=0&&lng!=0) {
            localDatabase.setCurrentLocation(lat, lng);
            String newTexts="";
            getBusinesses(newTexts);
            meterDistanceBetweenPoints(lat,lng,lat,lng);
        }
    }

    private float meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
    /*Location locationA = new Location("point A");

    locationA.setLatitude(lat_a);
    locationA.setLongitude(lng_a);

    Location locationB = new Location("point B");

    locationB.setLatitude(lat_b);
    locationB.setLongitude(lng_b);

    float distance = locationA.distanceTo(locationB);*/
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat_b - lat_a);
        double dLng = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);


       Log.d("thisulation", String.valueOf(dist));
        return dist;
    }
}
