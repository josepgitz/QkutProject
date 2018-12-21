package ke.co.qkut.qkut.views.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.dialogs.DialogListener;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;
import ke.co.qkut.qkut.views.dialogs.SmartPayInputScreenDialog;

import static ke.co.qkut.qkut.views.activities.MainActivity.fetchUser;

public class SignUpFragment extends Fragment {
    EditText fullname,email,password,confirm,mobile;
    Button signup;
    RadioGroup radioGroup;
    CountryCodePicker dial_code;
    JSONObject jsonObject= new JSONObject();
    ProgressBar progressBar;
    String code;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.signupfragment_layout,container,false);
        fullname= view.findViewById(R.id.fullname);
        email= view.findViewById(R.id.email);
        password=view.findViewById(R.id.password1);
        progressBar=view.findViewById(R.id.signUpProgress);
        confirm=view.findViewById(R.id.password2);
        radioGroup=view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        dial_code=view.findViewById(R.id.dial_code);

        mobile=view.findViewById(R.id.mobile);
        dial_code.registerCarrierNumberEditText(mobile);
        signup=view.findViewById(R.id.register_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDetails()){
                    try {

                        jsonObject.put("name",fullname.getText().toString());
                        jsonObject.put("mobile",mobile.getText());
                        jsonObject.put("email",email.getText());
                        jsonObject.put("device", android.os.Build.MODEL);
                        jsonObject.put("source", "android");
                        if (radioGroup.getCheckedRadioButtonId()==R.id.personal){
                            jsonObject.put("is_personal",0);
                        }else{
                            jsonObject.put("is_personal",1);

                        }
                        jsonObject.put("country_code",dial_code.getSelectedCountryName());
                        jsonObject.put("country_dial",dial_code.getSelectedCountryCode());
                        jsonObject.put("password",password.getText().toString());
                        requestCode(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return view;
    }
    private  boolean checkDetails(){
        if (TextUtils.isEmpty(fullname.getText())) {
            fullname.setError("Enter your name");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError("Invalid email address");
            return false;
        }else if(TextUtils.isEmpty(password.getText())||TextUtils.isEmpty(confirm.getText())) {
            password.setError("Password Empty");
            return false;
        }
        else if (!(password.getText().toString().matches(confirm.getText().toString()))){
            password.setError("Password mismatch");
            return false;
        }
        else if(password.getText().length()<6||confirm.getText().length()<6){
            password.setError("Password length too short <6!");
            return false;
        }
        return  true;
    }
    public void requestCode(final JSONObject jsonObject){
        Log.d("Verification",jsonObject.toString());

        progressBar.setVisibility(View.VISIBLE);
        signup.setEnabled(false);
        NetworkConnection.makeAPostRequest(URL.GET_VERIFACTION,jsonObject.toString(),null, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                progressBar.setVisibility(View.INVISIBLE);
                signup.setEnabled(true);
                Toast.makeText(getActivity(),"Please ensure you have an active internet connection",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                signup.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject jsonObject1= new JSONObject(remoteResponse.getMessage());
                    String status=jsonObject1.getString("status");
                    if (status.equals("bad")){
                        new GeneralDialogBuilder().model("Error",jsonObject1.getString("reason")).build(getActivity());
                    }else{
                        SmartPayInputScreenDialog smartPayInputScreenDialog= new SmartPayInputScreenDialog();
                        smartPayInputScreenDialog.setListenerList(new DialogListener() {
                            @Override
                            public void codeReceived(String code) {
                                try {

                                    jsonObject.put("verification",code)  ;
                                    registerUser(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        smartPayInputScreenDialog.show(getFragmentManager(),"code_dialog");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                NetworkConnection.remoteResponseLogger(remoteResponse);

            }
        });

    }
    public void registerUser(final JSONObject jsonPerson ) {
        progressBar.setVisibility(View.VISIBLE);
        signup.setEnabled(false);
        Log.e("User data for register",jsonPerson.toString());
        NetworkConnection.makeAPostRequest(URL.REGISTER_USER, jsonPerson.toString(), GLobalHeaders.getGlobalHeaders(getActivity()), new OnReceivingResult() {
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
                progressBar.setVisibility(View.INVISIBLE);
                signup.setEnabled(true);
                NetworkConnection.remoteResponseLogger(remoteResponse);
                String resultStatus = "";
                String resultData = "";
                JSONObject response = null;
                try {
                    response = new JSONObject(remoteResponse.getMessage());
                    resultStatus = response.getString("status").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (resultStatus.compareTo("ok") != 0) {
                    try {
                        Toast.makeText(getActivity(), response.getString("reason"), Toast.LENGTH_SHORT).show();
                        new GeneralDialogBuilder().model("Error",response.getString("reason")).build(getActivity());;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else { //good results

                    try {

                        Toast.makeText(getContext(), "Account created", Toast.LENGTH_SHORT).show();
                        UserLoginTask  mAuthTask = new UserLoginTask(jsonPerson.getString("email"), jsonPerson.getString("password"));
                        mAuthTask.execute((Void) null);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        JSONObject jsonPerson= new JSONObject();
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            try {
                jsonPerson.put("email", email);
                jsonPerson.put("password", password);
                jsonPerson.put("device", android.os.Build.MODEL);
                jsonPerson.put("source", "android");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            NetworkConnection.makeAPostRequest(URL.USER_LOGIN, jsonPerson.toString(), null, new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {
                    Toast.makeText(getContext(), "Problem Login in", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

                }

                @Override
                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                    try {
                        JSONObject jsonObject= new JSONObject(remoteResponse.getMessage());
                        if (jsonObject.getString("status").equals("bad")){
                            Toast.makeText(getContext(), "Wrong Username Or Password", Toast.LENGTH_SHORT).show();

                        }else{
                            String token=jsonObject.getString("access_token");
                            LocalDatabase.setToken((AppCompatActivity)getActivity(),token);
                            fetchUser((AppCompatActivity)getActivity());
                            Intent i = getContext().getPackageManager()
                                    .getLaunchIntentForPackage( getContext().getPackageName() );
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    NetworkConnection.remoteResponseLogger(remoteResponse);
                }

                @Override
                public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {

                }

                @Override
                public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {

                }

                @Override
                public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {

                }
            });
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }
}