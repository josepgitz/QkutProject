package ke.co.qkut.qkut.models;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ke.co.qkut.qkut.R;


public class ScheduledPlaceHandler_old {
    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    Context context;
    View parentView;
    private EditText toDateEtxt;
    private Queue activeQueue;
    private SimpleDateFormat dateFormatter;
    private TextView progressText;
    private ImageView progressWarning;
    TextView editDate,editTime, ticketTime;
    private Calendar myCalendar;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener time;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
    private View progressContainer;
    private  EditText DatePicker;
    private Button confirmQueueButton;
    private Button cancelQueueButton;
    private Button swap_Quest;
    private  BusinessInfo businessInfo;
    int year_x, month_x, day_x;
    int hour_x, minute_x;
    //constructor
    //----------------------------------------------------------------------------
    public ScheduledPlaceHandler_old(Context context, View parentView){
        progressDialog= new ProgressDialog(context);
        this.context = context;
        this.parentView = parentView;
        myCalendar = Calendar.getInstance();

    }
    public ScheduledPlaceHandler_old(Context context, View parentView, BusinessInfo businessInfo){
        progressDialog= new ProgressDialog(context);
this.businessInfo=businessInfo;
        this.context = context;
        this.parentView = parentView;
        myCalendar = Calendar.getInstance();

    }

    //initialize variables
    //---------------------------------------------------------------------------------------

    /*public void initialize(final Queue activeQueue) {

        this.activeQueue = activeQueue;
        this.progressContainer = parentView.findViewById(R.id.container_progress_indicator);
        this.progressText = parentView.findViewById(R.id.progress_indicator_text);
        this.progressWarning = parentView.findViewById(R.id.progress_indicator_icon);
        this.ticketTime=parentView.findViewById(R.id.TicketLabel);
        this.confirmQueueButton = parentView.findViewById(R.id.button_confirm_queue);
        this.cancelQueueButton = parentView.findViewById(R.id.button_cancel_queue2);
        cancelQueueButton.setEnabled(false);
        confirmQueueButton.setEnabled(false);
        this.swap_Quest = parentView.findViewById(R.id.button_swap1);
        swap_Quest.setEnabled(false);
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
        cancelQueueButton.setEnabled(true);
        confirmQueueButton.setEnabled(true);
        swap_Quest.setEnabled(true);


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

        if (activeQueue.getService().getIsQueued() != 0) {
            serviceQueueStatement.setText(Html.fromHtml("<b>You are Queued here:</b>"));
            cancelQueueButton.setEnabled(true);
            swap_Quest.setEnabled(false);
            this.confirmQueueButton.setText("Queued ");
            this.confirmQueueButton.setBackgroundColor(
                    context.getResources().getColor(R.color.colorDanger));
            this.swap_Quest.setBackgroundColor(
                    context.getResources().getColor(R.color.colorAccent));
            //serviceViewHolder.btnQueueHere.setPressed(true);
        } else {
            serviceQueueStatement.setText(Html.fromHtml("<b>Confirm you want to Queue here:</b>"));
            this.confirmQueueButton.setText("Confirm ");
            this.confirmQueueButton.setBackgroundColor(
                    context.getResources().getColor(R.color.colorPrimary));
        }
        this.confirmQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeQueue.getService().getIsQueued() == 0) {
                    confirmQueue(1);
                }
            }
        });
        this.cancelQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send registration details
                if (activeQueue.getService().getIsQueued() != 0) {
                    cancelConfirmedQueue();
                }

            }
        });
        this.swap_Quest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Start swap details

            }

        });


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
                cancelQueueButton.setEnabled(true);
                confirmQueueButton.setEnabled(true);
                swap_Quest.setEnabled(true);
                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        editTime.setText(String.format("%02d:%02d", hourOfDay, minutes) );
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
            int Day=myCalendar.get(Calendar.DAY_OF_MONTH);
            int Month=myCalendar.get(Calendar.MONTH);
            int Year=myCalendar.get(Calendar.YEAR);
             datePickerDialog= new DatePickerDialog(context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
             datePickerDialog= new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year,
                                      int monthOfYear, int dayOfMonth) {
                     myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDate();
                }
            } ,Day,Month,Year);
            updateDate();
            datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
            datePickerDialog.show();
        }
    });
}*/
    private void updateDate() {
        String dateString = sdf.format(myCalendar.getTimeInMillis());
        editDate.setText(dateString);
    }
    //progress bar show
    //----------------------------------------------------------------------------------
    private void ShowProgressBar(boolean show, boolean hasWarning, String message){
        if (progressContainer==null){

            progressDialog= new ProgressDialog(context);
            progressDialog.setTitle("Cancelling...");
            progressDialog.show();
        }else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }
    //confirm / cancel queue
    //----------------------------------------------------------------------------------------------
   /* public void cancelConfirmedQueue(){
        JSONObject jsonObject = new JSONObject();
        ShowProgressBar(true, false, "Confirming ");
        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_queue);
        String selectedDate = this.editDate.getText() + " " + this.editTime.getText();
        activeQueue.setScheduledTime(selectedDate);
        try {
            jsonObject.put("status", 0);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("created_through", context.getString(R.string.app_platform));
            jsonObject.put("id", activeQueue.getId());
            jsonObject.put("business_account_id", activeQueue.getBusinessAccount().getId());
            jsonObject.put("service_id", activeQueue.getService().getId());
            jsonObject.put("scheduled_time", activeQueue.getScheduledTime());
            jsonObject.put("id", 0);
            jsonObject.put("business_account_id", 0);
            jsonObject.put("service_id",0);
            jsonObject.put("scheduled_time", 0);
            Log.d("Queue", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String resultStatus = "";
                            String resultData = "";
                            try{
                                resultStatus = response.getString("status").toString();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                progressText.setText("Response error");
                            }
                            if(resultStatus.compareTo("ok") != 0){
                                try{
                                    resultData = response.getString("reason").toString();
                                    ShowProgressBar(true, false, resultData);

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                    ShowProgressBar(true, true, "System Error");
                                }
                            }
                            else{ //good results
                                try {
                                    resultData = response.get("reason").toString();
                                    progressText.setText(resultData);
                                    // Log.d("Queue good", resultData.toString());
                                    //  ViewSwapper.ShowMainView(parentView);
                                    ShowProgressBar(true, false, resultData);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                    ShowProgressBar(true, true, "System Data Error");
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.getMessage()==null)
                            {
                                progressDialog.setTitle("Canceled");
                            }else{

                                ConnectionHandler.decodeError(error);
                                Log.e("ConnectionHandler", error.getMessage());
                            }

                        }
                    }){

                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", UtilityFunctions.readAuthPersistentData(context).getAuthorizationToken());
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            ConnectionHandler.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //confirm / cancel queue
    //----------------------------------------------------------------------------------------------
    public void confirmQueue(int confirm){
        JSONObject jsonObject = new JSONObject();
        ShowProgressBar(true, false, "Confirming ");
        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_queue);
        String selectedDate = this.editDate.getText() + " " + this.editTime.getText();
        activeQueue.setScheduledTime(selectedDate);
        try {
            jsonObject.put("status", confirm);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("created_through", context.getString(R.string.app_platform));
            jsonObject.put("id", activeQueue.getId());
            jsonObject.put("business_account_id", activeQueue.getBusinessAccount().getId());
            jsonObject.put("service_id", activeQueue.getService().getId());
            jsonObject.put("scheduled_time", activeQueue.getScheduledTime());
            jsonObject.put("id", 0);
            jsonObject.put("business_account_id", 0);
            jsonObject.put("service_id",0);
            jsonObject.put("scheduled_time", 0);
            Log.d("Queue", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String resultStatus = "";
                        String resultData = "";
                        String ticketNumber="";
                        try{
                            resultStatus = response.getString("status").toString();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            progressText.setText("Response error");
                        }
                        if(resultStatus.compareTo("ok") != 0){
                            try{
                                resultData = response.getString("reason").toString();
                                ShowProgressBar(true, false, resultData);

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                ShowProgressBar(true, true, "System Error");
                            }
                        }
                        else{ //good results
                            try {
                                resultData = response.get("reason").toString();
                                progressText.setText(resultData);
                                ticketNumber=response.getJSONObject("data").getString("ticket");
                                ticketTime.setText(ticketNumber+" ");
                               // Log.d("Queue good", resultData.toString());
                              //  ViewSwapper.ShowMainView(parentView);
                                ShowProgressBar(true, false, resultData);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                ShowProgressBar(true, true, "System Data Error");
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getMessage()==null)
                        {
                            progressDialog.setTitle("Canceled");
                        }else{

                            ConnectionHandler.decodeError(error);
                            Log.e("ConnectionHandler", error.getMessage());
                        }

                    }
                }){

                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", UtilityFunctions.readAuthPersistentData(context).getAuthorizationToken());
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            ConnectionHandler.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //register a user
    //----------------------------------------------------------------------------------------------
    public void getServiceInfo(){

        ShowProgressBar(true, false, "Confirming ");

        String url = context.getString(R.string.app_base_link)
                + "/" + context.getString(R.string.url_service_info) + "/" + activeQueue.getService().getId() ;

        JSONObject jsonObject = new JSONObject();

        try {
            Log.d("Queue", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String resultStatus = "";
                            String resultData = "";
                            String ticketNumber="";
                            try{
                                resultStatus = response.getString("status").toString();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                progressText.setText("Response error");
                            }
                            if(resultStatus.compareTo("ok") != 0){
                                try{
                                    resultData = response.getString("reason").toString();
                                    ShowProgressBar(true, true, resultData);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                    ShowProgressBar(true, true, "System Error");
                                }
                            }
                            else{ //good results
                                try {
                                    ticketNumber=response.getJSONObject("data").toString();
                                    resultData = response.get("reason").toString();
                                    progressText.setText(resultData);
                                    ShowProgressBar(true, true, resultData);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                    ShowProgressBar(true, true, "System Data Error");
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressText.setText(ConnectionHandler.decodeError(error));
                            Log.e("ConnectionHandler", error.getMessage());
                        }
                    }){

                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", UtilityFunctions.readAuthPersistentData(context).getAuthorizationToken());
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            ConnectionHandler.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
