package ke.co.qkut.qkut.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.interfaces.LogInListener;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.activities.MainActivity;

import static ke.co.qkut.qkut.views.activities.MainActivity.fetchUser;

public class LoginFragment extends Fragment {
    Button mEmailSignInButton,signup;
private static LogInListener logInListener;
static SharedPreferences preferences;
ProgressBar progress_bar;
    public  static LogInListener getLogInListener() {
        return logInListener;
    }

    public static void setLogInListener(LogInListener logInListener) {
        LoginFragment.logInListener = logInListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_login_activty,container,false);
        super.onCreateView(inflater,container,savedInstanceState);
        progress_bar=view.findViewById(R.id.progress_bar);
        mEmailView = (AutoCompleteTextView)view.findViewById(R.id.email);
        signup=view.findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.viewPager.setCurrentItem(2);
            }
        });

        mPasswordView = (EditText)view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();

                    return true;
                }
                return false;
            }
        });
        preferences=getActivity().getApplicationContext().getSharedPreferences("prefs",Context.MODE_PRIVATE);
          mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();

            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        return  view;

    }

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private View mLoginFormView;

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if(!NetworkConnection.isConnectedToInternet(getActivity().getApplication())){
                Toast.makeText(getContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
                mEmailSignInButton.setEnabled(true);
                return true;
            }
            progress_bar.setVisibility(View.VISIBLE);
            mEmailSignInButton.setEnabled(false);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);

        }

        return false;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        JSONObject headers = new JSONObject();

        private final String mEmail;
        private final String mPassword;
        JSONObject jsonPerson= new JSONObject();
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            headers = GLobalHeaders.getGlobalHeaders((AppCompatActivity)getActivity());
            try {
                jsonPerson.put("email", mEmailView.getText().toString());
                jsonPerson.put("password", mPasswordView.getText().toString());
                jsonPerson.put("device", android.os.Build.MODEL);
                jsonPerson.put("source", "android");
                Log.d("LOGINDATA",jsonPerson.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            NetworkConnection.makeAPostRequest(URL.USER_LOGIN, jsonPerson.toString(), headers, new OnReceivingResult() {
                @Override
                public void onErrorResult(IOException e) {
                    Toast.makeText(getContext(), "Problem Login in", Toast.LENGTH_SHORT).show();
                    progress_bar.setVisibility(View.INVISIBLE);
                    mEmailSignInButton.setEnabled(true);
                }

                @Override
                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

                }

                @Override
                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                    mEmailSignInButton.setEnabled(true);
                    progress_bar.setVisibility(View.INVISIBLE);
                    try {
                        JSONObject jsonObject= new JSONObject(remoteResponse.getMessage());
                        if (jsonObject.getString("status").equals("bad")){
                            Toast.makeText(getContext(), "Wrong Username Or Password", Toast.LENGTH_SHORT).show();

                        }else{
                           String token=jsonObject.getString("access_token");
                            LocalDatabase.setToken((AppCompatActivity)getActivity(),token);
                            registerUserFirebaseToken((AppCompatActivity)getActivity());
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

    public static void registerUserFirebaseToken(AppCompatActivity appCompatActivity){
        LocalDatabase localDatabase=new LocalDatabase(preferences);
        String s=localDatabase.getFireBaseToken(appCompatActivity);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("token",s);

Log.d("ToeknsFromDb",s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkConnection.makeAPostRequest(URL.USER_TOKEN, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(appCompatActivity), new OnReceivingResult() {
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
