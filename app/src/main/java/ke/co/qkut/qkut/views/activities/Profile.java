package ke.co.qkut.qkut.views.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.QkutBase;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.models.Person;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.dialogs.DialogListener;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;
import ke.co.qkut.qkut.views.dialogs.SmartPayInputScreenDialog;

public class Profile extends AppCompatActivity {
    ImageView profileImage;
    TextView VerificationCodeTexView,progress_indicator_text, profileNameTexview, EmailNoTexView, MobileNoTexView, TexViewAccountType, editusername, editaccounttype, editmobile, editemail;
    EditText profileName;
    EditText EmailNo;
    EditText MobileNo;
    EditText AccountType;
    EditText MobileVerify;
    Button Edit, Save;
    AlertDialog alertDialog;
    AlertDialog alertDialogEmail;
    AlertDialog alertDialogMobile;
    AlertDialog alertDialogAccountType;
    AlertDialog alertDialogforMobileVeificatio;
    LinearLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.my_account_activity);
        profileImage = findViewById(R.id.nav_profile_photo1);
        progress_indicator_text=findViewById(R.id.progress_indicator_text);

        //-----get id for edit icon
        loading=findViewById(R.id.loading);
        editusername = findViewById(R.id.editusername);
        editaccounttype = findViewById(R.id.editaccounttype);
        editmobile = findViewById(R.id.editmobile);
        editemail = findViewById(R.id.editemail);
        ///---end of getting id of edit icons
        Save = findViewById(R.id.save);
        EmailNoTexView = findViewById(R.id.EmailNo);
        MobileNoTexView = findViewById(R.id.MobileNo);
        profileNameTexview = findViewById(R.id.profile_name);
        TexViewAccountType = findViewById(R.id.AccountType);
        Person person = QkutBase.getUser();
        if (person.getProfileByte() == null) {
            profileImage.setImageResource(person.getDefault());
        } else {
            profileImage.setImageBitmap(person.getProfileByte());
        }
        //=========---------------------------------------------------------------------------------
        //Get View info form the database
        //=========---------------------------------------------------------------------------------
        profileNameTexview.setText(person.getName());
        EmailNoTexView.setText(Html.fromHtml("" + person.getEmail()));
        MobileNoTexView.setText(Html.fromHtml("" + person.getMobile()));
        profileName = new EditText(this);
        EmailNo = new EditText(this);
        MobileVerify = new EditText(this);
        MobileNo = new EditText(this);
        TexViewAccountType = new EditText(this);
        //=========---------------------------------------------------------------------------------
        //Dialog View setup
        //=========---------------------------------------------------------------------------------

        alertDialogforMobileVeificatio = new AlertDialog.Builder(this).create();
        alertDialogforMobileVeificatio.setTitle("Verification Code");
        alertDialogforMobileVeificatio.setView(MobileVerify);
        alertDialogforMobileVeificatio.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 JSONObject obl=new JSONObject();
                 try{
                     obl.put("code",MobileVerify.getText());
                     editProfile(obl);

                 }catch (JSONException E){

                     E.printStackTrace();
                 }
                requestCode();
            }
        });


        //=========---------------------------------------------------------------------------------
        //Dialog View setup
        //=========---------------------------------------------------------------------------------

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Edit Profile");
        alertDialog.setView(profileName);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileNameTexview.setText(profileName.getText());

                if (Save.getVisibility() == View.GONE) {
                    Save.setVisibility(View.VISIBLE);
                }

            }
        });

        // =========---------------------------------------------------------------------------------
        //Edit Account Type
        //=========---------------------------------------------------------------------------------

        alertDialogAccountType = new AlertDialog.Builder(this).create();
        alertDialogAccountType.setTitle("Edit Email");
        alertDialogAccountType.setView(AccountType);
        alertDialogAccountType.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TexViewAccountType.setText(AccountType.getText());
                if (Save.getVisibility() == View.GONE) {
                    Save.setVisibility(View.VISIBLE);
                }
            }
        });
        //=========---------------------------------------------------------------------------------
        //Edit Email
        //=========---------------------------------------------------------------------------------

        alertDialogEmail = new AlertDialog.Builder(this).create();
        alertDialogEmail.setTitle("Edit Email");
        alertDialogEmail.setView(EmailNo);
        alertDialogEmail.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EmailNoTexView.setText(EmailNo.getText());
                if (Save.getVisibility() == View.GONE) {
                    Save.setVisibility(View.VISIBLE);
                }
            }
        });

        //=========---------------------------------------------------------------------------------
        //Edit MobileName
        //=========---------------------------------------------------------------------------------

        alertDialogMobile = new AlertDialog.Builder(this).create();
        alertDialogMobile.setTitle("Edit Mobile");
        alertDialogMobile.setView(MobileNo);
        alertDialogMobile.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MobileNoTexView.setText(MobileNo.getText());
                if (Save.getVisibility() == View.GONE) {
                    Save.setVisibility(View.VISIBLE);
                }


            }
        });


        //----------------------------------------------
        //====--------this is a function to  profile name
        //----------------------------------------------

        editusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileName.setText(profileNameTexview.getText());
                alertDialog.show();
            }
        });
        //----------------------------------------------
        //-----save changes for Email after Editting
        //----------------------------------------------

        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailNo.setText(EmailNoTexView.getText());
                alertDialogEmail.show();
            }
        });
        //----------------------------------------------
        //-----save changes for Mobile after Editting
        //----------------------------------------------
        editmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileNo.setText(MobileNoTexView.getText());
                alertDialogMobile.show();
            }
        });

        if (person.getVerified().equals("1")) {

        }


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCode();


                //  profileName.setEnabled(true);
            }
        });
        super.onCreate(savedInstanceState);

    }

    public void requestCode() {
        loading.setVisibility(View.VISIBLE);
        final JSONObject editObject = new JSONObject();
        try {
            editObject.put("name",profileNameTexview.getText().toString());
            editObject.put("mobile", MobileNoTexView.getText().toString());
            editObject.put("email", EmailNoTexView.getText().toString());
            editObject.put("country_dial", "+254");
            Log.d("Verification",editObject.toString());
            NetworkConnection.makeAPostRequest(URL.GET_VERIFACTION, editObject.toString(), null, new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {

                    Toast.makeText(Profile.this, "Please ensure you have an active internet connection", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                    NetworkConnection.remoteResponseLogger(remoteResponse);
                    Log.d("verification",remoteResponse.toString());

                }

                @Override
                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {

                    try {
                        JSONObject jsonObject1 = new JSONObject(remoteResponse.getMessage());
                        String status = jsonObject1.getString("status");
                        if (status.equals("bad")) {
                            new GeneralDialogBuilder().model("Error", jsonObject1.getString("reason")).build(Profile.this);
                        } else {
                            SmartPayInputScreenDialog smartPayInputScreenDialog = new SmartPayInputScreenDialog();
                            smartPayInputScreenDialog.setListenerList(new DialogListener() {
                                @Override
                                public void codeReceived(String code) {
                                    loading.setVisibility(View.GONE);
                                    try {

                                      editObject.put("verification", code);
                                        editProfile(editObject);
                                        alertDialogforMobileVeificatio.show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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


        } catch (Exception E) {
            E.printStackTrace();
        }
    }
////------------------------------------------Saving new Record fo Account------------------------
public void editProfile(JSONObject jsonObject){

   loading.setVisibility(View.VISIBLE);
    progress_indicator_text.setText("Updating Profile");

            NetworkConnection.makeAPostRequest(URL.GET_VERIFACTION, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(Profile.this), new OnReceivingResult() {
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
                    }
                    if (resultStatus.equals("200")) {
                        try {
                            //
                            resultData = response.getString("message").toString();
                            loading.setVisibility(View.GONE);
                            //        resultData = response.get("reason").toString();
                            Toast.makeText(Profile.this, "Registered Sucessfully", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            Toast.makeText(Profile.this, "Ooops!Registered Failed", Toast.LENGTH_LONG).show();

                            //
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

    }

}

