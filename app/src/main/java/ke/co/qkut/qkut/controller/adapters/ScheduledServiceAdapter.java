package ke.co.qkut.qkut.controller.adapters;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.schedule_models.ScheduleInfo;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.activities.MainActivity;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;


public class ScheduledServiceAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    DatePickerDialog datePickerDialog;
    private View progressContainer;
    DatePickerDialog.OnDateSetListener date;
    private AppCompatActivity context;
    private List <ScheduleInfo> listData;
    private Calendar myCalendar;
    String dateFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
    private TextView progressText;
    private  static  int counter=0;
    private ImageView progressWarning;

    View view;
    private ViewPager viewPager;
    public ScheduledServiceAdapter(AppCompatActivity context, List <ScheduleInfo> listData, View view) {
        this.context = context;
        if (!(context==null)){
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        this.listData = listData;
        myCalendar = Calendar.getInstance();
        this.view=view;

    }

    @Override
    public int getCount() {
        return (listData.size());
    }

    @Override
    public ScheduleInfo getItem(int position) {

        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final ViewHolder rowHolder;
        convertView = layoutInflater.inflate(R.layout.scheduled_place_row, parent, false);
        final ScheduleInfo scheduleInfo=listData.get(position);
        rowHolder = new ViewHolder();


        for(int i =0; i < listData.size(); i++){
                    rowHolder.serviceName =  convertView.findViewById(R.id.service_name);
                    rowHolder.serviceDescription = convertView.findViewById(R.id.service_description);
                    rowHolder.serviceDepartment = convertView.findViewById(R.id.service_department);
                    rowHolder.btnQueueInfo = convertView.findViewById(R.id.button_queued_info);
                    rowHolder.timelabel=convertView.findViewById(R.id.timeLabel);
                    rowHolder.btnswap=convertView.findViewById(R.id.button_swap1);
                    rowHolder.swaptime=convertView.findViewById(R.id.swap_time);
                            rowHolder.serviceQueueStatement = convertView.findViewById(R.id.queue_status_statement);
                    rowHolder.serviceNameView = convertView.findViewById(R.id.confirm_service_name);
                    rowHolder. serviceDepartmentView = convertView.findViewById(R.id.confirm_service_department);
                    rowHolder.companyNameView = convertView.findViewById(R.id.confirm_company_name);
                    rowHolder. compnayAddress = convertView.findViewById(R.id.confirm_company_address);
                    rowHolder. queueSize1 = convertView.findViewById(R.id.service_queue_size);
                    rowHolder.confirmswap=convertView.findViewById(R.id.btn_confirm_swap);
                    rowHolder. queueSpeed1 = convertView.findViewById(R.id.service_queue_speed);
                    rowHolder. queueSize = convertView.findViewById(R.id.my_service_queue_size);
                    rowHolder.btnCancelSchedule=convertView.findViewById(R.id.button_cancel_queue);
                    rowHolder.btnCancelSwap=convertView.findViewById(R.id.btn_cancel_swap);
                    rowHolder. queueSpeed = convertView.findViewById(R.id.my_service_queue_speed);
                    rowHolder.timelabel=convertView.findViewById(R.id.timeLabel);
                    rowHolder.DatePicker=convertView.findViewById(R.id.DatePicker);
                    rowHolder.ticketNo=convertView.findViewById(R.id.ticketNo);
                      if(scheduleInfo.getIs_queue().equals("1")){
                         rowHolder.ticketNo.setText(Html.fromHtml("<b>TicketNo:<b>")+scheduleInfo.getTicket());
                    rowHolder.serviceName.setText(Html.fromHtml("<b><b>") + scheduleInfo.getService());
                    rowHolder.serviceDescription.setText(Html.fromHtml("<b>Description:<b>") + scheduleInfo.getDescription());
                    rowHolder.serviceDepartment.setText(Html.fromHtml("<b>Department:<b>") + scheduleInfo.getDepartment());
                    rowHolder.queueSize.setText(Html.fromHtml("<b>Queue size:<b>" + scheduleInfo.getQueue_size()+""));
                    rowHolder.queueSpeed.setText(Html.fromHtml("<b>QueueSpeed:<b>" + scheduleInfo.getQueue_speed() + ""));
                    rowHolder.queueSize1.setText(Html.fromHtml("<b>Queue size:<b>" +scheduleInfo.getQueue_size()  + ""));
                    rowHolder.queueSpeed1.setText(Html.fromHtml("<b>QueueSpeed:<b>" + scheduleInfo.getQueue_speed() + ""));
                    rowHolder.compnayAddress.setText(Html.fromHtml("<b>Company Address:<b>") +scheduleInfo.getAddress());
                    rowHolder.serviceDepartmentView.setText(Html.fromHtml("<b>Department:<b>") + scheduleInfo.getDepartment());
                    rowHolder.serviceNameView.setText(Html.fromHtml("<b>Service:<b>") + scheduleInfo.getService());
                    rowHolder.companyNameView.setText(Html.fromHtml("<b>Company Name:<b>") +scheduleInfo.getBusiness());
                    rowHolder.timelabel.setText(Html.fromHtml("<b>At:<b>") + scheduleInfo.getScheduled_time());
                    rowHolder.DatePicker.setText(Html.fromHtml("<b>At:<b>") + scheduleInfo.getScheduled_time());
                    rowHolder.serviceQueueStatement.setText("You have queued for");
                    rowHolder.btnQueueInfo.setText("Queue Info ");
                    }else{
                        rowHolder.serviceQueueStatement.setText("You scheduled a visit");
                        rowHolder.btnQueueInfo.setText("Visit Info ");
                         rowHolder.swaptime.setText(myCalendar.getTime().toString());
                          rowHolder.serviceName.setText(Html.fromHtml("<b><b>") + scheduleInfo.getService());
                          rowHolder.serviceDescription.setText(Html.fromHtml("<b>Description:<b>") + scheduleInfo.getDescription());
                          rowHolder.serviceDepartment.setText(Html.fromHtml("<b>Department:<b>") + scheduleInfo.getDepartment());
                          rowHolder.btnswap.setVisibility(View.GONE);
                          rowHolder.compnayAddress.setText(Html.fromHtml("<b>Company Address:<b>") +scheduleInfo.getAddress());
                          rowHolder.serviceDepartmentView.setText(Html.fromHtml("<b>Department:<b>") + scheduleInfo.getDepartment());
                          rowHolder.serviceNameView.setText(Html.fromHtml("<b>Service:<b>") + scheduleInfo.getService());
                          rowHolder.companyNameView.setText(Html.fromHtml("<b>Company Name:<b>") +scheduleInfo.getBusiness());
                          rowHolder.timelabel.setText(Html.fromHtml("<b>At:<b>") + scheduleInfo.getScheduled_time());
                          rowHolder.DatePicker.setText(Html.fromHtml("<b>At:<b>") + scheduleInfo.getScheduled_time());


                    }

                    }
                   final View queueView = convertView.findViewById(R.id.confirm_queue_form);
                   final View swapView = convertView.findViewById(R.id.swap);
                    rowHolder.btnQueueInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(queueView.getVisibility()==View.VISIBLE){
                                queueView.setVisibility(View.GONE);
                            }else {
                                queueView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    rowHolder.btnCancelSchedule.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CancelSchedule(scheduleInfo);
                        }
                    });
                    rowHolder.btnCancelSwap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swapView.setVisibility(View.GONE);
                            queueView.setVisibility(View.VISIBLE);
                        }
                    });
                    rowHolder.btnswap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(swapView.getVisibility()==View.VISIBLE){
                                swapView.setVisibility(View.GONE);
                                queueView.setVisibility(View.VISIBLE);
                            }else {
                                swapView.setVisibility(View.VISIBLE);
                                queueView.setVisibility(View.GONE);
                            }
                        }
                    });


                    rowHolder.confirmswap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            swapQueuePosition(scheduleInfo,rowHolder.swaptime.getText().toString());

                        }
                    });

        rowHolder.swaptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = myCalendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        rowHolder.swaptime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, currentHour, currentMinute, false);

                rowHolder.swaptime.setText(String.format("%02d:%02d", myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE)));
                timePickerDialog.show();
            }
        });

if(scheduleInfo.getIs_queue().equals("1")){


    rowHolder.btnQueueInfo.setBackgroundColor(
            context.getResources().getColor(R.color.colorPrimary));


}
else{

    rowHolder.btnQueueInfo.setBackgroundColor(
            context.getResources().getColor(R.color.colorDanger));
}

        return convertView;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager=viewPager;
    }

    class ViewHolder{
        TextView serviceName;
        TextView serviceDepartment;
        TextView serviceDescription;
        TextView queueSize;
        TextView queueSpeed;
        TextView queueSize1;
        TextView queueSpeed1;
        Button btnQueueInfo;
        Button  btnHome;
        TextView DatePicker;
        TextView serviceQueueStatement;
        TextView serviceNameView;
        TextView serviceDepartmentView;
        Button btnswap;
        TextView companyNameView;
        TextView swaptime;
        TextView compnayAddress;
        Button confirmswap;
        Button btnCancelSwap;
        TextView ticketNo;
        Button btnCancelSchedule;
        TextView timelabel;
    }


    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Cancelling...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }

//    }
public void CancelSchedule(ScheduleInfo scheduleInfo){

    ShowProgressBar(true,false,"Cancelling");
        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_queue);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", scheduleInfo.getId());
            jsonObject.put("business_account_id", scheduleInfo.getBusiness_id());
            jsonObject.put("service_id",scheduleInfo.getService_id());
            jsonObject.put("scheduled_time", new DateTime().monthOfYear().get() + "/" + new DateTime().dayOfMonth().get() + "/"
                    + new DateTime().year().get() + " " + new DateTime().hourOfDay().get() + ":" + new DateTime().getMinuteOfHour());
            jsonObject.put("status", 0);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("created_through", context.getString(R.string.app_platform));
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.USER_CONFIRM_VISIT, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {
                 e.printStackTrace();
                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);
                }

                @Override
                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);
                    String resultStatus = "";
                    String resultData = "";
                    JSONObject response = new JSONObject();
                    try {
                        response = new JSONObject(remoteResponse.getMessage());
                        resultStatus = response.getString("status").toString();
                        Log.d("Results", resultStatus);
                    } catch (JSONException e) {
                        e.printStackTrace();
                       // progressText.setText("Response error");
                    }
                    if (resultStatus.compareTo("ok") != 0) {
                        try {
                            resultData = response.getString("reason").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else { //good results
                        try {
                            progressDialog.dismiss();
                            resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Cancelled", "Queue Cancelled:" ).build(context);
                            MainActivity.viewPager.setCurrentItem(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Errors", e.getMessage());
                            new GeneralDialogBuilder().model("Cancelled", "Queue could not be cancel").build(context);
                        }
                    }
                }

                @Override
                public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }
            });
        } catch (JSONException e) {

        }
    }

    public void swapQueuePosition(final ScheduleInfo scheduleInfo, final String SwapTime){

        ShowProgressBar(true,false,"Cancelling");
        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_queue);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("business_account_id", scheduleInfo.getBusiness_id());
            jsonObject.put("service_id", scheduleInfo.getService_id());
            jsonObject.put("queue_id",scheduleInfo.getId());
            jsonObject.put("status", 1);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("scheduled_time",SwapTime);
            jsonObject.put("created_through", context.getString(R.string.app_platform));
            Log.d("Swap12", jsonObject.toString());
            NetworkConnection.makeAPostRequest(URL.SWAP_QUEUE, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                    String resultStatus = "";
                    String resultData = "";
                    String ticketNumber;
                    JSONObject response = new JSONObject();
                    try {
                        response = new JSONObject(remoteResponse.getMessage());
                        resultStatus = response.getString("status").toString();
                        Log.d("Results", resultStatus);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // progressText.setText("Response error");
                    }
                    if (resultStatus.compareTo("ok") != 0) {
                        try {
                            resultData = response.getString("reason").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else { //good results
                        try {
                            progressDialog.dismiss();
                            resultData = response.get("reason").toString();
                            new GeneralDialogBuilder().model("Successful", "You Swaped Successfully:\n Your will be served at: \n" +SwapTime).build(context);
                            MainActivity.viewPager.setCurrentItem(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Errors", e.getMessage());
                            new GeneralDialogBuilder().model("Cancelled", "Queue could not be cancel").build(context);
                        }
                    }
                }

                @Override
                public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }
            });
        } catch (JSONException e) {

        }




    }

}
