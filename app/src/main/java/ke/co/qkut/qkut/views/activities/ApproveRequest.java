package ke.co.qkut.qkut.views.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ke.co.qkut.qkut.controller.adapters.ApproveSchedulesAdapter;
import ke.co.qkut.qkut.controller.adapters.ApproveViewPagerAdapter;
import ke.co.qkut.qkut.controller.adapters.ConfirmScheduleAdapter;
import ke.co.qkut.qkut.controller.adapters.ScheduledServiceAdapter;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.models.ConfirmManager;
import ke.co.qkut.qkut.models.ConfirmSchedule;
import ke.co.qkut.qkut.models.ConfirmScheduledList;
import ke.co.qkut.qkut.schedule_models.ScheduleManager;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;

public class ApproveRequest extends AppCompatActivity {
RecyclerView recyclerView;

    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    private View progressContainer;
    private  AppCompatActivity appCompatActivity;
    private TextView progressText;
    private ImageView progressWarning;
    TextView showWhenNothing;
    ListView ScheduledPlacesList;
public ConfirmSchedule listData;
    RelativeLayout loadingScheduledBusiness;

    ApproveViewPagerAdapter approveViewPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    List<Integer> items= new ArrayList<>();
    List<ConfirmSchedule> confirmSchedules=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);
        ScheduledPlacesList=findViewById(R.id.listOfRequest);
        showWhenNothing=findViewById(R.id.showWhenNothing);
        getScheduledPlaces();
        loadingScheduledBusiness=findViewById(R.id.loadingScheduledBusiness);
        ScheduledPlacesList=findViewById(R.id.listOfRequest);
    }

    public void getScheduledPlaces() {
      //  loadingScheduledBusiness.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        headers = GLobalHeaders.getGlobalHeaders(ApproveRequest.this);
        String url = URL.GET_SCHEDULES_TO_CONFIRM;
        Log.e("globalheaders",headers.toString());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                Log.e("Logged", "Please check Your Network");
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                Log.e("Logged", remoteResponse.getMessage());
            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                try {
                    JSONObject jsonObject = new JSONObject(remoteResponse.getMessage().toString());
                    String resultStatus = jsonObject.getString("status");
                    if (resultStatus.equals("ok")) {
                        loadingScheduledBusiness.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){
                                ConfirmSchedule confirmSchedule=new ConfirmSchedule();
                            }
                            List<ConfirmSchedule> confirmScheduleList = ConfirmScheduledList.getconfirmScheduleFrom(jsonObject.toString());
                            ApproveSchedulesAdapter approveSchedulesAdapter = new ApproveSchedulesAdapter( (AppCompatActivity) ApproveRequest.this,confirmScheduleList);
                            ScheduledPlacesList.setAdapter(approveSchedulesAdapter);
                            approveSchedulesAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
    }


}
