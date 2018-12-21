package ke.co.qkut.qkut.models;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;

public class VisitHandler {

    AppCompatActivity context;
    View parentView;

    private Visit activeVisit;
    private TextView progressText;
    private ImageView progressWarning;
    private View progressContainer;
    TextView editDate;
    TextView editTime;
    private Button confirmVisitButton;
    private Button ConfirmtoQueue;
    private Button cancelVisitButton;
    int currentHour;
    int currentMinute;
    String amPm;
    private SimpleDateFormat dateFormatter;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    DatePickerDialog.OnDateSetListener time;
    private EditText DatePicker;
    private Button confirmQueueButton;
    private Button btnHomeFromVisit;
    private Button cancelQueueButton;
    private Button swap_Quest;
    private  View view;
    final String SERVER_KEY = "AAAAUTBiP4c:APA91bFoXb6tfQ7WdK78wp3lGCFeX5fjpVY6t1MQjs0sCO1im_eqHFfUUm4TUbaPAvq0_apWXZUIdNp_Qqn4THNYmpgJxwT4WW8-1YDTbQaJaGOWU9YbH97TmzNZ9yUDzdxXQ9C5ubHZ";

    //constructor
    //----------------------------------------------------------------------------
    public VisitHandler(AppCompatActivity context, View parentView) {

        this.context = context;
        this.parentView = parentView.findViewById(R.id.activity_visit);
        view=parentView;
        myCalendar = Calendar.getInstance();

    }


    //initialize variables
    //---------------------------------------------------------------------------------------

    public void initialize(final Visit activeVisit) {

        this.activeVisit = activeVisit;
        this.progressContainer = parentView.findViewById(R.id.container_progress_indicator);
        this.progressText = parentView.findViewById(R.id.progress_indicator_text);
        this.progressWarning = parentView.findViewById(R.id.progress_indicator_icon);
        this.confirmVisitButton = parentView.findViewById(R.id.button_confirm_visit);
        this.cancelVisitButton = parentView.findViewById(R.id.button_cancel_visit);
        this.btnHomeFromVisit=parentView.findViewById(R.id.button_Home_fromVisit);
                TextView serviveVisitStatement = parentView.findViewById(R.id.visit_status_statement);
        TextView serviveNameView = parentView.findViewById(R.id.confirm_service_name);
        TextView serviveDepartmentView = parentView.findViewById(R.id.confirm_service_department);
        TextView companyNameView = parentView.findViewById(R.id.confirm_company_name);
        TextView compnayAddress = parentView.findViewById(R.id.confirm_company_address);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        editDate = parentView.findViewById(R.id.DatePicker);
        editTime = parentView.findViewById(R.id.TimePicker);
        currentHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = myCalendar.get(Calendar.MINUTE);
        editTime.setText(String.format("%02d:%02d", currentHour, currentMinute));
        serviveNameView.setText(Html.fromHtml("<b>Service:</b> " + activeVisit.getService().getName()));
        serviveDepartmentView.setText(Html.fromHtml("<b>Department:</b> " + activeVisit.getService().getDepartmentName()));
        companyNameView.setText(Html.fromHtml("<b>Company:</b> "
                + activeVisit.getBusinessAccount().getName()));
        compnayAddress.setText(Html.fromHtml("<b>Address:</b> "
                + activeVisit.getBusinessAccount().getAddress()
                + "<br /><b>Industry:</b> " + activeVisit.getBusinessAccount().getSubIndustry()
                + " | " + activeVisit.getBusinessAccount().getIndustry()));

        if (activeVisit.getService().getIsVisited() != 0) {
            serviveVisitStatement.setText(Html.fromHtml("<b>You are a visitor here:</b>"));
            this.confirmVisitButton.setText("Visiting ");
//            this.confirmVisitButton.setBackgroundColor(
//                    context.getResources().getColor(R.color.colorDanger));
            //serviceViewHolder.btnVisitHere.setPressed(true);
        } else {
            serviveVisitStatement.setText(Html.fromHtml("<b>Confirm you want to visit here:</b>"));
            this.confirmVisitButton.setText("Confirm ");
//            this.confirmVisitButton.setBackgroundColor(
//                    context.getResources().getColor(R.color.colorAccent));
        }
        this.confirmVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeVisit.getService().getIsVisited() == 0) {
                    confirmVisit(1);
                    sendVisitPush();
                }
            }
        });

        this.cancelVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send registration details
                if (activeVisit.getService().getIsVisited() != 0) {
                    confirmVisit(0);
                }
            }
        });
        this.btnHomeFromVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome(view);
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
//        this.ConfirmtoQueue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //send registration details
//                if (activeVisit.getService().getIsVisited() != 0) {
//                    confirmVisit(1);
//                }
//            }
//        });


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
}
    private void updateDate() {

        String dateString = sdf.format(myCalendar.getTimeInMillis());
        editDate.setText(dateString);

    }
    //----------------------------------------------------------------------------------
    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {

        progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
        progressText.setText(message);

    }

    public void ShowHome(View view){

        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View VisitActivity= view.findViewById(R.id.activity_visit);
        View QueueActivity= view.findViewById(R.id.activity_queue);
        business_list_view.setVisibility(View.VISIBLE);
        QueueActivity.setVisibility(View.GONE);
        VisitActivity.setVisibility(View.GONE);

    }
    public void ShowVisit(View view){

        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View VisitActivity= view.findViewById(R.id.activity_visit);
        View QueueActivity= view.findViewById(R.id.activity_queue);
        business_list_view.setVisibility(View.GONE);
        QueueActivity.setVisibility(View.GONE);
        VisitActivity.setVisibility(View.VISIBLE);

    }
    public void hideVisit(View view){

        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View VisitActivity= view.findViewById(R.id.activity_visit);
        View QueueActivity= view.findViewById(R.id.activity_queue);
        business_list_view.setVisibility(View.VISIBLE);
        QueueActivity.setVisibility(View.VISIBLE);
        VisitActivity.setVisibility(View.GONE);

    }

    public void showHome(View view){

        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View VisitActivity= view.findViewById(R.id.activity_visit);
        View QueueActivity= view.findViewById(R.id.activity_queue);
        business_list_view.setVisibility(View.VISIBLE);
        QueueActivity.setVisibility(View.GONE);
        VisitActivity.setVisibility(View.GONE);

    }

    private void sendVisitPush() {

        String msg = "You scheduled a visit";
        String title = "Visit";
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

    public void confirmVisit(int confirm){

        ShowProgressBar(true, false, "Confirming ");

        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_visit);
        String selectedDate = this.editDate.getText() + " " + this.editTime.getText();
        activeVisit.setscheduledTime(selectedDate);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", activeVisit.getId());
            jsonObject.put("business_account_id", activeVisit.getBusinessAccount().getId());
            jsonObject.put("service_id", activeVisit.getService().getId());
            jsonObject.put("scheduled_time", activeVisit.getScheduledTime());
            jsonObject.put("status", confirm);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("created_through", context.getString(R.string.app_platform));
            Log.d("Visit2222", jsonObject.toString());
            NetworkConnection.makeAPostRequest(URL.USER_CONFIRM_VISIT, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShowProgressBar(true, true, "System Error");
                        }
                    } else { //good results
                        try {
                            resultData = response.get("reason").toString();
                            progressText.setText(resultData);
                            ShowProgressBar(false, false, resultData);
                            new GeneralDialogBuilder().model("Scheduled","Scheduled successfully, pending confirmation").build(context);
                            hideVisit(view);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShowProgressBar(true, true, "System Data Error");
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
        }catch (JSONException e){

        }

    }
}
