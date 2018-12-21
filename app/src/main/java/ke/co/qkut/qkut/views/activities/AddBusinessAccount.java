package ke.co.qkut.qkut.views.activities;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.GeocodingLocation;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;

public class AddBusinessAccount extends AppCompatActivity {
EditText industry,name , country,description,location;
AutoCompleteTextView city;
    CountryCodePicker dial_code;
    AutoCompleteTextView textView,neighbour;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business_account);
        dial_code=findViewById(R.id.dial_code);
        industry=findViewById(R.id.industry);
        name=findViewById(R.id.Name);
        country=findViewById(R.id.country);
        city=findViewById(R.id.City);
        neighbour=findViewById(R.id.neighbourhood);
        description=findViewById(R.id.Description);
        location=findViewById(R.id.PhysicalLocation);
        dial_code.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country.setText(dial_code.getSelectedCountryName());
                Log.d("Country",dial_code.getDefaultCountryCode());
            }
        });
        String[] recourseList=this.getResources().getStringArray(R.array.CountryCodes);
        textView = (AutoCompleteTextView) findViewById(R.id.City);
        String[] countries = getResources().getStringArray(R.array.CountryCodes);
        String[] Towns = getResources().getStringArray(R.array.Towns);
         adapter = new ArrayAdapter<String>(AddBusinessAccount.this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);
        adapter1 = new ArrayAdapter<String>(AddBusinessAccount.this, android.R.layout.simple_list_item_1, Towns);
        textView.setAdapter(adapter1);
        textView.setThreshold(1);
        neighbour.setAdapter(adapter);
        neighbour.setThreshold(1);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = city.getText().toString();

                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getApplicationContext(), new GeocoderHandler());

            }
        });
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Toast.makeText(AddBusinessAccount.this,locationAddress,Toast.LENGTH_LONG).show();
            }
    }

    public void getNeigbourhood() {
       // loadinworkshops.setVisibility(View.VISIBLE);
        JSONObject headers = new JSONObject();
        String url = URL.NEIGBOURHOOD;
        headers = GLobalHeaders.getGlobalHeaders(AddBusinessAccount.this);
        Log.e("globalheaders",headers.toString());
        NetworkConnection.makeAGetRequest(url, headers, new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
                // Log.e("Logged", e.getMessage());
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

                    if (resultStatus.equals("200")) {
                     //   loadinworkshops.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {

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
}
