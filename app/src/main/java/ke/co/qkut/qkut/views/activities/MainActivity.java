package ke.co.qkut.qkut.views.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ke.co.qkut.qkut.Activity_Approve_Schedule;
import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.QkutBase;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.controller.adapters.PageAdapter;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.interfaces.FireBaseToken;
import ke.co.qkut.qkut.interfaces.LogInListener;
import ke.co.qkut.qkut.messages.MyFirebaseMessagingService;
import ke.co.qkut.qkut.models.Person;
import ke.co.qkut.qkut.qrcodescanner_activity;
import ke.co.qkut.qkut.util.location.UserLocation;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.dialogs.QRDialog;
import ke.co.qkut.qkut.views.dialogs.SortDialog;
import ke.co.qkut.qkut.views.fragments.LoginFragment;

import static ke.co.qkut.qkut.util.location.UserLocation.MY_PERMISSIONS_REQUEST_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener,LogInListener, FireBaseToken {
    public static  ViewPager viewPager;
    private  static  PageAdapter pageAdapter;
    private  static  String [] PAGES_NAME_NOT_LOGGED_IN= new String[]{"Home","Login","Sign Up"};
    private  static String [] PAGES_NAME_LOGGED_IN= new String[]{"Home","Scheduled","Alerts"};
    static TextView textView;
    static ImageView nav_profile_photo,sort;
    static NavigationView navigationView;
    static     DrawerLayout drawer;
    FloatingActionButton floatingActionButton;
    private UserLocation userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyFirebaseMessagingService.registerFirebaseToken(this);
        sort=findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortDialog sortDialog= new SortDialog();
                sortDialog.show(getSupportFragmentManager(),"sort");
            }
        });
        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRDialog qrDialog= new QRDialog();
                qrDialog.show(getSupportFragmentManager(), "user");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.addDrawerListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view=navigationView.getHeaderView(0);
        textView=view.findViewById(R.id.nav_profile_name);
        nav_profile_photo=view.findViewById(R.id.nav_profile_photo);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void createNotice() {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "1");
        notificationBuilder.setContentTitle("david");
        notificationBuilder.setSmallIcon(R.mipmap.logo_qkut);
        notificationBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName()+R.raw.sound_effect_message ));
        NotificationManager notificationManager =(NotificationManager)getApplicationContext(). getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1 + 2 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  Toast.makeText(this,"back key is pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        String token;
        userLocation=new UserLocation(MainActivity.this);
        viewPager= findViewById(R.id.pager);
        Log.e("Tokenes",LocalDatabase.getToken(this));
        Log.e("notloggedin",LocalDatabase.NOT_LOGGED_IN);
        if ((LocalDatabase.getToken(this)).equals(LocalDatabase.NOT_LOGGED_IN)){
            QkutBase.setPerson(setDefaultUser());
       //     LoginFragment.registerUserFirebaseToken(this);
            LoginFragment.setLogInListener(this);
            pageAdapter=new PageAdapter( viewPager,MainActivity.this,PAGES_NAME_NOT_LOGGED_IN);
            pageAdapter.setLoggedIn(false);
            viewPager.setAdapter(pageAdapter);


        }else{
            FirebaseApp.initializeApp(MainActivity.this);
            LoginFragment.registerUserFirebaseToken(MainActivity.this);
            Log.d("FireBaseTokens",  LocalDatabase.getFireBaseToken(MainActivity.this));
            fetchUser(MainActivity.this);

            pageAdapter=new PageAdapter( viewPager,MainActivity.this,PAGES_NAME_LOGGED_IN);
            pageAdapter.setLoggedIn(true);
            viewPager.setAdapter(pageAdapter);


        }
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        super.onResume();
    }
public Person setDefaultUser(){
      Person person=new Person();
      person.setName("Hi Guest");
      person.setEmail("Not Available");
      person.setCountry_code("Not Available");
        person.setMobile("Not Available");
        person.setCountryDial("Not Available");
        person.setStatus("Not Available");
        person.setVerified("Not Available");
        person.setId(0);
        return person;
}
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.nav_list_places:
            {
                viewPager.setCurrentItem(0);
                drawer.closeDrawer(Gravity.START);
                break;
            }
            case R.id.nav_account_logout:{

                    LocalDatabase.clearToken(MainActivity.this);
                    Intent i = MainActivity.this.getPackageManager().getLaunchIntentForPackage(MainActivity.this.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;

            }
            case R.id.my_account:{

            if (LocalDatabase.getToken(MainActivity.this) == LocalDatabase.NOT_LOGGED_IN) {
                Toast.makeText(MainActivity.this, "To View Profile You Need to log in", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1, true);
                drawer.closeDrawer(Gravity.START);
            } else {
                Intent i =new Intent(MainActivity.this,Profile.class);
                startActivity(i);
                break;
            }

            }
            case R.id.qrscan:{
                Intent i=new Intent(MainActivity.this, qrcodescanner_activity.class);
                startActivity(i);
                break;

            }
            case R.id.businessAccount:{
                Intent i=new Intent(MainActivity.this, AddBusinessAccount.class);
                startActivity(i);
                break;

            }

            case R.id.reviewSchedules:{
                Intent i=new Intent(MainActivity.this, Activity_Approve_Schedule.class);
                startActivity(i);
                break;

            }
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
       return true;
    }
    public  static void fetchUser(final AppCompatActivity appCompatActivity){
        NetworkConnection.makeAGetRequest(URL.USER_ACCOUNT, GLobalHeaders.getGlobalHeaders(appCompatActivity), new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {
            }
            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
            }
            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                try {
                    JSONObject jsonObject= new JSONObject(remoteResponse.getMessage());
                    Person person=Person.getPerson(jsonObject.getString("data"));
                    getProfilePicture(person,appCompatActivity);

                    QkutBase.setPerson(person);
                    textView.setText(person.getName());
                    if ( LoginFragment.getLogInListener()!=null){
                        LoginFragment.getLogInListener().onLogin(person);
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
    }
    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }
    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
    }
    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
    }
    @Override
    public void onDrawerStateChanged(int newState) {
        if (DrawerLayout.STATE_IDLE==newState){

        }else{
        }
    }
    public static  void getProfilePicture(final Person person, AppCompatActivity appCompatActivity){
        NetworkConnection.downloadImage(URL.USER_PROFILE_PIC, GLobalHeaders.getGlobalHeaders(appCompatActivity), new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {

            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                person.setProfileByte(remoteResponse.getResponseBody());
                nav_profile_photo.setImageBitmap(person.getProfileByte());
                NetworkConnection.remoteResponseLogger(remoteResponse);
                // Toast.makeText(appCompatActivity, "", Toast.LENGTH_SHORT).show();
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
    }


    @Override
    public void onLogin(Person person) {

    }

    @Override
    public void onReceivingFireBaseToken(String token) {
       String FirebaseToken=token;
        String LocalToken=LocalDatabase.getToken(this);
        Log.d("FirebaseToken",FirebaseToken);
        Log.d("LocalToken",LocalToken);
        if ((LocalDatabase.getToken(this)).equals(LocalDatabase.NOT_LOGGED_IN)){
            LocalDatabase.saveFirebaseToken(this,token);
            LoginFragment.registerUserFirebaseToken(this);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if(grantResults.length>0){
                    if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                        userLocation.requestLocationNow();
                    }
                }
                break;
        }


    }
}
