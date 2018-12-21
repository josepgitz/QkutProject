package ke.co.qkut.qkut;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.controller.adapters.ApproveSchedulesAdapter;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.models.ConfirmSchedule;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;

public class Activity_Approve_Schedule extends AppCompatActivity {
ListView ScheduleTobeApproved;
TextView noSchedules;
RelativeLayout approvinLoading;
List<ConfirmSchedule> lisdata=new ArrayList<>();

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__approve__schedule);
        ScheduleTobeApproved=findViewById(R.id.ScheduleTobeApproved);
        noSchedules=findViewById(R.id.noSchedules);
        approvinLoading=findViewById(R.id.approvinLoading);
        getPendinApprovalWorkshop(Activity_Approve_Schedule.this);
    }
    public void getPendinApprovalWorkshop(final Context context) {
        approvinLoading.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_SCHEDULES_TO_CONFIRM;

        headers = GLobalHeaders.getGlobalHeadersforApprove(Activity_Approve_Schedule.this);

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
                    String reason=jsonObject.getString("reason");

                    if (resultStatus.equals("ok")) {
                        approvinLoading.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){
                                ConfirmSchedule confirmSchedule = ConfirmSchedule.getConfirmScheduleFrom1(jsonArray.getJSONObject(i).toString());
                                lisdata.add(confirmSchedule);
                                ApproveSchedulesAdapter approveSchedulesAdapter = new ApproveSchedulesAdapter( (AppCompatActivity) Activity_Approve_Schedule.this,lisdata);
                                ScheduleTobeApproved.setAdapter(approveSchedulesAdapter);
                                approveSchedulesAdapter.notifyDataSetChanged();

                            }
                        }
                    }else if(resultStatus.equals("bad")){
                        approvinLoading.setVisibility(View.GONE);
                        ScheduleTobeApproved.setVisibility(View.GONE);
                        noSchedules.setText(reason);

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
