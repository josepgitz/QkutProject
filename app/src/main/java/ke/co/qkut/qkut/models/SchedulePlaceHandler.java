package ke.co.qkut.qkut.models;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;

public class SchedulePlaceHandler {

    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    AppCompatActivity context;
    private Service service;
    private Department department;
    View parentView;
    private EditText toDateEtxt;
    private SimpleDateFormat dateFormatter;
    private Queue activeQueue;
    private TextView progressText;
    private ImageView progressWarning;
    TextView editDate, editTime, ticketTime;
    private Calendar myCalendar;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener time;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
    private View progressContainer;
    private EditText DatePicker;
    private Button btnSwap;
    private Button btnCancel;
    private BusinessInfo businessInfo;

    //constructor
    //----------------------------------------------------------------------------
    public SchedulePlaceHandler(AppCompatActivity context, View parentView) {
        progressDialog = new ProgressDialog(context);
        this.context = context;
        this.parentView = parentView;
        myCalendar = Calendar.getInstance();

    }

    //initialize variables
    //---------------------------------------------------------------------------------------
    public void initialize(final Department department, final Service service) {
        this.service = service;
        this.department = department;
        this.progressContainer = parentView.findViewById(R.id.container_progress_indicator);
        this.progressText = parentView.findViewById(R.id.progress_indicator_text);
        this.progressWarning = parentView.findViewById(R.id.progress_indicator_icon);
        btnSwap = parentView.findViewById(R.id.button_swap1);
        btnCancel = parentView.findViewById(R.id.button_cancel_queue);
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


    //-----------------------------------ConfirmQueue Request----------------------------------
    public void confirmService(int confirm) {

        ShowProgressBar(true, false, "Confirming ");

        String url = context.getString(R.string.app_base_link) + "/" + context.getString(R.string.url_user_queue);

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("scheduled_time", new DateTime().monthOfYear().get() + "/" + new DateTime().dayOfMonth().get() + "/" + new DateTime().year().get() + " " + new DateTime().hourOfDay().get() + ":" + new DateTime().getMinuteOfHour());
            jsonObject.put("id", activeQueue.getId());
            jsonObject.put("business_account_id", activeQueue.getBusinessAccount().getId());
            jsonObject.put("service_id", activeQueue.getService().getId());
            jsonObject.put("status", confirm);
            jsonObject.put("device", android.os.Build.MODEL);
            jsonObject.put("created_through", context.getString(R.string.app_platform));

            NetworkConnection.makeAPostRequest(URL.ADD_USER_TO_QUEUE, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(context), new OnReceivingResult() {
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
                    progressDialog.dismiss();
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
                            ShowProgressBar(false, true, resultData);
                            //  ViewSwapper.ShowMainView(parentView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShowProgressBar(false, true, "System Error");
                        }
                    } else { //good results
                        progressDialog.dismiss();
                        try {
                            resultData = response.get("reason").toString();
                            ticketNumber = response.getJSONObject("data").get("ticket").toString();
                            progressText.setText(resultData);

                            new GeneralDialogBuilder().model("Queued", "Your Ticket Number Is" + ticketNumber).build(context);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Errors", e.getMessage());
                            new GeneralDialogBuilder().model("Queued", "You Did Not Get A ticket Number").build(context);
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


        // Access the RequestVisit through your singleton class.
        //   ConnectionHandler.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

}
