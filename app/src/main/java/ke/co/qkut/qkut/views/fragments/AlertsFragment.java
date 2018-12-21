package ke.co.qkut.qkut.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ke.co.qkut.qkut.Activity_Approve_Schedule;
import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.controller.adapters.AlertAdapter;
import ke.co.qkut.qkut.controller.adapters.ApproveSchedulesAdapter;
import ke.co.qkut.qkut.interfaces.OnReceiveingNewService;
import ke.co.qkut.qkut.messages.MessageReceivers;
import ke.co.qkut.qkut.models.ConfirmSchedule;
import ke.co.qkut.qkut.models.Service;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;

public class AlertsFragment extends Fragment  {
ListView listView;
private  static List<Service> serviceList= new ArrayList<>();
List<ConfirmSchedule> listdata=new ArrayList<>();
//AlertAdapter alertAdapter;
FrameLayout no_notification_frame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_alert,container,false);
        listView= view.findViewById(R.id.alert_list_view);
        no_notification_frame=view.findViewById(R.id.no_notification_frame);
       // listView.setAdapter(alertAdapter=new AlertAdapter());
      //  MessageReceivers.addNewServiceListener(alertAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        getPendinApprovalWorkshop(getActivity());
        return view;
    }


    public void getPendinApprovalWorkshop(final Context context) {
        no_notification_frame.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.GET_SCHEDULES_TO_CONFIRM;
        headers = GLobalHeaders.getGlobalHeaders(context);
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
                        no_notification_frame.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.length()>0){
                                //   ConfirmSchedule confirmSchedule=new ConfirmSchedule();

                                ConfirmSchedule confirmSchedule = ConfirmSchedule.getConfirmScheduleFrom1(jsonArray.getJSONObject(i).toString());
                                listdata.add(confirmSchedule);
                                AlertAdapter approveSchedulesAdapter = new AlertAdapter( (AppCompatActivity) getActivity(),listdata);
                                listView.setAdapter(approveSchedulesAdapter);
                                approveSchedulesAdapter.notifyDataSetChanged();

                            }


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

//
//    class AlertAdapter extends BaseAdapter implements OnReceiveingNewService{
//
//        @Override
//        public int getCount() {
//            if (serviceList.size()>0){
//                no_notification_frame.setVisibility(View.GONE);
//
//            }else{
//                no_notification_frame.setVisibility(View.VISIBLE);
//
//            }
//            return serviceList.size();
//
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return serviceList.get(position);
//
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            if (convertView==null)
//            {
//                convertView=LayoutInflater.from(getContext()).inflate(R.layout.activity_alert_row,parent,false);
//
//
//
//
//            }
//            if (position==0){
//                convertView.findViewById(R.id.image_new).setVisibility(View.VISIBLE);
//            }
//           final LinearLayout linearLayout=convertView.findViewById(R.id.moreItemAlert);
//           final Button button= convertView.findViewById(R.id.more_button);
//           button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (linearLayout.getVisibility()==View.VISIBLE){
//                        linearLayout.setVisibility(View.GONE);
//                        button.setText("More");
//
//                    }else{
//                        linearLayout.setVisibility(View.VISIBLE);
//                        button.setText("Less");
//
//
//
//                    }
//                   // changeColor(convertView);
//                }
//            });
//            return convertView;
//        }

//        @Override
//        public void OnRecevingNewServiceNotication(final Service service) {
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    serviceList= new ArrayList<>();
//                    serviceList.add(service);
//                    notifyDataSetChanged();
//
//                }
//            });
//            Log.e("new service","recived");
//
      }




