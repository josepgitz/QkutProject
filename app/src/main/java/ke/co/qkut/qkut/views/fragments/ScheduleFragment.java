package ke.co.qkut.qkut.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ke.co.qkut.qkut.Modeler.Message;
import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.controller.adapters.ScheduledServiceAdapter;
import ke.co.qkut.qkut.models.BusinessInfo;
import ke.co.qkut.qkut.schedule_models.ScheduleInfo;
import ke.co.qkut.qkut.schedule_models.ScheduleManager;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;

public class ScheduleFragment extends Fragment {

    ListView ScheduledPlacesList;
    ViewPager viewPager;
    RelativeLayout loadingScheduledBusiness;
    FloatingActionButton fab;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.scheduled_places_layout,container,false);
        loadingScheduledBusiness=view.findViewById(R.id.loadingScheduledBusiness);
        ScheduledPlacesList=view.findViewById(R.id.Scheduled_list_view);

        viewPager= getActivity().findViewById(R.id.pager);
        getScheduledPlace();
        return view;
    }

    @Override
    public void onResume() {
        getScheduledPlace();
        super.onResume();
    }

    public void getScheduledPlace() {
        JSONObject headers = new JSONObject();
        String url=URL.GET_SCHEDULES;
        headers= GLobalHeaders.getGlobalHeaders((AppCompatActivity)getActivity());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                Log.e("Logged",e.getMessage());
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged",remoteResponse.getMessage());
            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                ScheduleManager scheduleManager=ScheduleManager.setScheduleManager(remoteResponse.getMessage());
                Log.e("Logged",remoteResponse.getMessage());
                String resultStatus = "";
                String resultData = "";
                JSONObject response = new JSONObject();
                try {
                    response= new JSONObject(remoteResponse.getMessage());
                    resultStatus = response.getString("status").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (scheduleManager.getStatus().equals("ok")){
                    loadingScheduledBusiness.setVisibility(View.GONE);
                    ScheduledServiceAdapter scheduledServiceAdapter = new ScheduledServiceAdapter( (AppCompatActivity) getActivity(),scheduleManager.getData(),viewPager );

                    scheduledServiceAdapter.setViewPager(viewPager);
                    ScheduledPlacesList.setAdapter(scheduledServiceAdapter);

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
    public void populateScheduledList(JSONArray jsonScheduledplaces) {

        List<BusinessInfo> ScheduledServiceInfoList= new ArrayList<>();
        for (int i = 0; i < jsonScheduledplaces.length(); i++) {
            try {
                Log.d("Valley", jsonScheduledplaces.getJSONObject(i).toString());
                ScheduledServiceInfoList.add(new BusinessInfo(jsonScheduledplaces.getJSONObject(i),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("ScheduledInfo",jsonScheduledplaces.toString()+"");



    }


}
