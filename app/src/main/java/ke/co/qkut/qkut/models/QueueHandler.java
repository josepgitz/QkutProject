package ke.co.qkut.qkut.models;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.activities.MainActivity;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;
import ke.co.qkut.qkut.views.fragments.ScheduleFragment;

public class QueueHandler {
    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    AppCompatActivity context;
    private Queue activeQueue;
    View parentView;
    private EditText toDateEtxt;
    private SimpleDateFormat dateFormatter;
    private TextView progressText;
    private ImageView progressWarning;
    TextView editDate, editTime, ticketTime;
    private Calendar myCalendar;
    String dateFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener time;

    private View progressContainer;
    private EditText DatePicker;
    private Button confirmQueueButton;
    private Button cancelQueueButton;
    private BusinessInfo businessInfo;
    private Button buttongoHome;
    private  View mainview;
    final String SERVER_KEY = "AAAAUTBiP4c:APA91bFoXb6tfQ7WdK78wp3lGCFeX5fjpVY6t1MQjs0sCO1im_eqHFfUUm4TUbaPAvq0_apWXZUIdNp_Qqn4THNYmpgJxwT4WW8-1YDTbQaJaGOWU9YbH97TmzNZ9yUDzdxXQ9C5ubHZ";

    //constructor
    //----------------------------------------------------------------------------
    public QueueHandler(AppCompatActivity context, View parentView) {
        progressDialog = new ProgressDialog(context);
        this.context = context;
        this.parentView = parentView.findViewById(R.id.activity_queue);
        mainview = parentView;
        myCalendar = Calendar.getInstance();
    }


    public void initialize(final Queue activeQueue) {

        this.activeQueue = activeQueue;
        this.progressContainer = parentView.findViewById(R.id.container_progress_indicator);
        this.progressText = parentView.findViewById(R.id.progress_indicator_text);
        this.progressWarning = parentView.findViewById(R.id.progress_indicator_icon);

        this.confirmQueueButton = parentView.findViewById(R.id.button_confirm_queue2);
//        this.confirmQueueButton.setEnabled(true);
        this.buttongoHome=parentView.findViewById(R.id.button_Home_queue);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        TextView serviceQueueStatement = parentView.findViewById(R.id.queue_status_statement);
        TextView serviceNameView = parentView.findViewById(R.id.confirm_service_name);
        TextView serviceDepartmentView = parentView.findViewById(R.id.confirm_service_department);
        TextView companyNameView = parentView.findViewById(R.id.confirm_company_name);
        TextView compnayAddress = parentView.findViewById(R.id.confirm_company_address);
        TextView queueSize = parentView.findViewById(R.id.service_queue_size);
        TextView queueSpeed = parentView.findViewById(R.id.service_queue_speed);
        editDate = parentView.findViewById(R.id.DatePicker);
        editTime = parentView.findViewById(R.id.TimePicker);
        currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = myCalendar.get(Calendar.MINUTE);



        editTime.setText(String.format("%02d:%02d", currentHour, currentMinute));
        serviceNameView.setText(Html.fromHtml("<b>Service:</b> " + activeQueue.getService().getName()));

        serviceDepartmentView.setText(Html.fromHtml("<b>Department:</b> "
                + activeQueue.getService().getDepartmentName()));
        companyNameView.setText(Html.fromHtml("<b>Company:</b> "
                + activeQueue.getBusinessAccount().getName()));
        compnayAddress.setText(Html.fromHtml("<b>Address:</b> "
                + activeQueue.getBusinessAccount().getAddress()
                + "<br /><b>Industry:</b> " + activeQueue.getBusinessAccount().getSubIndustry()
                + " | " + activeQueue.getBusinessAccount().getIndustry()));
        queueSize.setText("Queue Size: " + activeQueue.getService().getQueueSize());
        queueSpeed.setText("Queue Speed: " + activeQueue.getService().getQueueSpeed());


        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);
        // set calendar date and update editDate
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, Calendar.YEAR);
                myCalendar.set(Calendar.MONTH, Calendar.MONTH);
                myCalendar.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
                updateDate();
            }

        };
        // onclick - popup Timepicker
        editTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = myCalendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        editTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    }
                }, currentHour, currentMinute, false);

                editTime.setText(String.format("%02d:%02d", myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE)));
                timePickerDialog.show();
            }
        });
        // onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                // TODO Auto-generated method stub
                int Day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int Month = myCalendar.get(Calendar.MONTH);
                int Year = myCalendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDate();
                    }
                }, Day, Month, Year);
                updateDate();
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        this.confirmQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeQueue.getService().getIsQueued() == 0) {
                    confirmQueue(1);

                }
            }
        });
        this.buttongoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome(mainview,false);

            }
        });
    }

    private void updateDate() {
        String dateString = sdf.format(myCalendar.getTimeInMillis());
        editDate.setText(dateString);
    }
    private void refreshApp() {
        MainActivity.viewPager.setCurrentItem(0,true);
    }

    //progress bar show
    //----------------------------------------------------------------------------------
    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Processing...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }

    public void confirmQueue(int confirm) {
        ShowProgressBar(true, false, "Confirming ");
        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_queue);
        String selectedDate = this.editDate.getText() + " " + this.editTime.getText();
        activeQueue.setScheduledTime(selectedDate);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", activeQueue.getId());
            jsonObject.put("business_account_id", activeQueue.getBusinessAccount().getId());
            jsonObject.put("service_id", activeQueue.getService().getId());
            jsonObject.put("scheduled_time", activeQueue.getScheduledTime());
            jsonObject.put("status", confirm);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("created_through", context.getString(R.string.app_platform));
            Log.d("Visit1222", jsonObject.toString());

            NetworkConnection.makeAPostRequest(URL.ADD_USER_TO_QUEUE, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {

                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

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
                        progressText.setText("Response error");
                    }
                    if (resultStatus.compareTo("ok") != 0) {
                        try {
                            resultData = response.getString("reason").toString();
                            ShowProgressBar(true, true, resultData);
                            //  ViewSwapper.ShowMainView(parentView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShowProgressBar(true, true, "System Error");
                        }
                    } else { //good results
                        progressDialog.dismiss();
                        try {
                            resultData = response.get("reason").toString();
                            ticketNumber = response.getJSONObject("data").get("ticket").toString();
                            progressText.setText(resultData);
                            new GeneralDialogBuilder().model("Queued", "Your ticket number is :" + ticketNumber).build(context);
                           ShowPlaces(mainview,false);
                            sendFCMPush();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Errors", e.getMessage());
                            new GeneralDialogBuilder().model("Queued", "You did not get a ticket number ").build(context);
                        }
                    }
                }
                @Override
                public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {

                }

                @Override
                public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);

                }

                @Override
                public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                }
            });
        } catch (JSONException e) {
        }
    }
    public void ShowPlaces(View view,boolean state){
        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View QueueActivity= view.findViewById(R.id.activity_queue);
        View VisitActivity= view.findViewById(R.id.activity_visit);
        business_list_view.setVisibility(View.VISIBLE);
        QueueActivity.setVisibility(View.GONE);
        VisitActivity.setVisibility(View.GONE);
    }
    public static void ShowQueue(View view){
        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View QueueActivity= view.findViewById(R.id.activity_queue);
        View VisitActivity= view.findViewById(R.id.activity_visit);
        business_list_view.setVisibility(View.GONE);
        QueueActivity.setVisibility(View.VISIBLE);
        VisitActivity.setVisibility(View.GONE);


    }
    public void showHome(View view,boolean state){
        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View QueueActivity= view.findViewById(R.id.activity_queue);
        View VisitActivity= view.findViewById(R.id.activity_visit);
        business_list_view.setVisibility(View.VISIBLE);
        QueueActivity.setVisibility(View.GONE);
        VisitActivity.setVisibility(View.GONE);
    }




    private void sendFCMPush() {

        String msg = "Confirmed, you have queued successfully";
        String title = "Queue";
        String token = LocalDatabase.getFireBaseToken(context);

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();
            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name
            objData.put("tag", token);
            objData.put("priority", "high");
            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);
            obj.put("to", token);

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("return here>>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
NetworkConnection.makeAPostRequest(URL.SEND_NOTIFICATION, objData.toString(), getHeaders(), new OnReceivingResult() {
    @Override
    public void onErrorResult(IOException e) {

    }

    @Override
    public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
        NetworkConnection.remoteResponseLogger(remoteResponse);

    }

    @Override
    public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
        NetworkConnection.remoteResponseLogger(remoteResponse);

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


    }
    public JSONObject getHeaders()  {
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("Authorization","key=" + SERVER_KEY);
            jsonObject.put("Content-Type", "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }
};


