package com.texasimaginology.ticms;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.texasimaginology.ticms.ClassRoutine.ClassRoutineFragment;
import com.texasimaginology.ticms.Counseling.Counseling_Fragment;
import com.texasimaginology.ticms.Dashboard.DashboardFragment;
import com.texasimaginology.ticms.Dashboard.TeacherDashboard;
import com.texasimaginology.ticms.Login.LoginFragment;
import com.texasimaginology.ticms.NavigationViewItems.About.AboutFragment;
import com.texasimaginology.ticms.NavigationViewItems.Contact.ContactFragment;
import com.texasimaginology.ticms.NavigationViewItems.News.NewsFragment;
import com.texasimaginology.ticms.Notification.OfflineNotification.RoutineNotificationReceiver;
import com.texasimaginology.ticms.Notification.OfflineNotification.RoutineNotificationService;
import com.texasimaginology.ticms.Notification.PushNotification.AllNotification;
import com.texasimaginology.ticms.Notification.sendNotification.SendNotificationActivity;
import com.texasimaginology.ticms.RoomDatabase.TicmsRoomDatabase;
import com.texasimaginology.ticms.RoomDatabase.UserLoginResponse.UserLoginResponseDao;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private Fragment fragment;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        if ("".equals(sharedPref.getString("token", "")))
            showHideNavMenuAccordingToLoginRole(sharedPref.getString("loginType",""));

        //If started from the offline notification then lunch Teacher dashboard
        Intent intent= getIntent();
        if(intent!=null){
            Log.d("Inside intent check", "True");
            onNewIntent(intent);
            Log.d("Inside intent check end", "True");

        }else {
            Log.d("Inside else intent chk", "True");
            displaySelectedScreen(R.id.nav_home);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
//        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
//        if ("".equals(sharedPref.getString("token", ""))) {
//            showHideNavMenuAccordingToLoginRole(sharedPref.getString("loginType",""));
//            Menu menu = navigationView.getMenu();
//            MenuItem login = menu.findItem(R.id.nav_login);
//            MenuItem logout = menu.findItem(R.id.nav_logout);
//            MenuItem home = menu.findItem(R.id.nav_home);
//            MenuItem notification = menu.findItem(R.id.nav_notification);
//            MenuItem classRoutine=menu.findItem(R.id.nav_classRoutine);
//            MenuItem counselling = menu.findItem(R.id.nav_counseling);
//            home.setTitle("Home");
//            home.setIcon(R.drawable.ic_home_grey_24dp);
//            notification.setVisible(false);
//            login.setVisible(true);
//            logout.setVisible(false);
//            classRoutine.setVisible(false);
//            counselling.setVisible(false);

//        }
//        else {
//            Menu menu = navigationView.getMenu();
//            MenuItem login = menu.findItem(R.id.nav_login);
//            MenuItem logout = menu.findItem(R.id.nav_logout);
//            MenuItem home = menu.findItem(R.id.nav_home);
//            MenuItem notification = menu.findItem(R.id.nav_notification);
//            MenuItem classRoutine=menu.findItem(R.id.nav_classRoutine);
//            MenuItem counselling = menu.findItem(R.id.nav_counseling);
//            counselling.setVisible(false);
//            logout.setVisible(true);
//            login.setVisible(false);
//            home.setTitle("Dashboard");
//            home.setIcon(R.drawable.ic_dashboard_grey_24dp);
//            if (sharedPref.getString("userrole", "").equals("ADMIN")
//                    || sharedPref.getString("userrole", "").equals("CUSTOMER")) {
//                notification.setVisible(true);
//                classRoutine.setVisible(true);
//                counselling.setVisible(true);
//            }
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem showNotification = menu.findItem(R.id.act_notification);
        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        if (!("".equals(sharedPref.getString("token", "")))) {
            showNotification.setVisible(true);
        }else
            showNotification.setVisible(false);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ((id == R.id.act_notification)) {
//            if(checkInternetConnection.isNetworkAvailable()==false){
//                Toast.makeText(MainActivity.this,"Please turn on your wifi",Toast.LENGTH_LONG).show();
//            }
//            startActivity(new Intent(this, AllNotification.class));
//            if (checkInternetConnection.doInBackground()==false){
//                Toast.makeText(MainActivity.this,"Server is not responding",Toast.LENGTH_LONG).show();
//            }
//            else{
            Intent showNotifications = new Intent(this, AllNotification.class);
            startActivity(showNotifications);
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
        }

        return true;
    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new MainFragment();
                setActionBarTitle("Home");
//                if (home.getTitle().equals("Home")) {
//
//                } else {
//                    fragment = new LoginFragment();
//                    getSupportActionBar().setTitle("Login");
//                }
                break;
            case R.id.nav_dashboard:
                fragment = new DashboardFragment();
                setActionBarTitle("Dashboard");
                break;
            case R.id.nav_my_routine:
                fragment= new TeacherDashboard();
                setActionBarTitle("My Routine");
                break;
            case R.id.nav_news:
                fragment = new NewsFragment();
                setActionBarTitle("News");
                break;
            case R.id.nav_send_notification:
                startActivity(new Intent(this, SendNotificationActivity.class));
                break;
            case R.id.nav_login:
                fragment = new LoginFragment();
                setActionBarTitle("Login");
                break;
            case R.id.nav_contact:
                fragment = new ContactFragment();
                setActionBarTitle("Contact");
                break;
            case R.id.nav_classRoutine:
                fragment = new ClassRoutineFragment();
                setActionBarTitle("Class Routine");
                break;
            case R.id.nav_counseling:
                fragment = new Counseling_Fragment();
                setActionBarTitle("Counselling");
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                setActionBarTitle("About Us");
                break;
            case R.id.nav_logout:
                //Clear the user Id and token from shared preferences
                SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", "");
                editor.putInt("id", 0);
                editor.apply();

                //Delete loginInstance from database
                TicmsRoomDatabase ticmsRoomDatabase = TicmsRoomDatabase.getDatabaseInstance(this);
                UserLoginResponseDao userLoginResponseDao= ticmsRoomDatabase.userLoginResponseDao();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        userLoginResponseDao.deleteAllLoginInstances();

                        return null;
                    }
                }.execute();

                //Cancel all scheduled routine notification
                Intent intent= new Intent();
                PendingIntent pendingIntent= PendingIntent.getActivity(this, RoutineNotificationReceiver.NOTIFICATION_ID,intent, 0);
                AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                pendingIntent.cancel();
                alarmManager.cancel(pendingIntent);

                Menu menu = navigationView.getMenu();
                MenuItem dashboard = menu.findItem(R.id.nav_dashboard);
                MenuItem login = menu.findItem(R.id.nav_login);
                MenuItem logout = menu.findItem(R.id.nav_logout);
                MenuItem sendNotification = menu.findItem(R.id.nav_send_notification);
                MenuItem classRoutine=menu.findItem(R.id.nav_classRoutine);
                MenuItem counselling = menu.findItem(R.id.nav_counseling);
                MenuItem myRoutine = menu.findItem(R.id.nav_my_routine);

                dashboard.setVisible(false);
                logout.setVisible(false);
                sendNotification.setVisible(false);
                classRoutine.setVisible(false);
                counselling.setVisible(false);
                myRoutine.setVisible(false);

                login.setVisible(true);

                fragment = new MainFragment();

//                FragmentManager fm = getSupportFragmentManager();
//                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                    fm.popBackStack();
//                }
//
//                //Get Token and UserId from shared preferences.
//                SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
//                LogoutDto logoutDto = new LogoutDto(sharedPref.getString("token", ""));
//                int userId = sharedPref.getInt("id", 0);
//
//                //Calling Api using Retrofit
//                ApiInterface apiService =
//                        ApiClient.getClient().create(ApiInterface.class);
//
//                //Since there is Empty response when successful so <ResponseBody> is used.
//                Call<ResponseBody> call = apiService.logout(userId, logoutDto);
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                        //Executes when Response is sucessful.
//                        if (response.code() == 200 || response.code() == 401) {
//                            Toast.makeText(MainActivity.this, "Sucessfully Logout", Toast.LENGTH_SHORT).show();
//
//                            //Clear the user Id and token from shared preferences
//                            SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPref.edit();
//                            editor.putString("token", "");
//                            editor.putInt("id", 0);
//                            editor.apply();
//                            fragment = new LoginFragment();
//
//                        } else {
//                            //Maps the error message in ErrorMessageDto
//                            JsonParser parser = new JsonParser();
//                            JsonElement mJson = null;
//                            try {
//                                mJson = parser.parse(response.errorBody().string());
//                                Gson gson = new Gson();
//                                ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
//                                //mProgressBar.setVisibility(View.GONE);
//                                Toast.makeText(MainActivity.this, errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
//                            } catch (IOException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//
//                    }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
//                    }
//                });
                break;
        }

        //replacing the fragment
        if (fragment != null)

        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            /*if(!fragment.equals(MainFragment.class)){
                ft.addToBackStack("previous");
            }*/
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setActionBarTitle(String actionBarTitle) {
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(actionBarTitle);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras= intent.getExtras();
        if(extras!= null && extras.containsKey("FromOfflineNotification")){
            showMessage("Texas Class Alert!!!", extras.getString("NotificationBody",""));

            fragment=new TeacherDashboard();
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            setActionBarTitle("My Routine");
            ft.commit();
        }else
            displaySelectedScreen(R.id.nav_login);
    }

    private void showHideNavMenuAccordingToLoginRole(String loginType) {
        Menu menu = navigationView.getMenu();
        MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem sendNotification = menu.findItem(R.id.nav_send_notification);
        MenuItem classRoutine = menu.findItem(R.id.nav_classRoutine);
        MenuItem myRoutine = menu.findItem(R.id.nav_my_routine);
        MenuItem counseling = menu.findItem(R.id.nav_counseling);
        MenuItem dashboard = menu.findItem(R.id.nav_dashboard);
        login.setVisible(false);
        logout.setVisible(true);

        switch (loginType){
            case "ADMIN":
                dashboard.setVisible(true);
                counseling.setVisible(true);
                sendNotification.setVisible(true);
                classRoutine.setVisible(true);
                //Invisible item to admin
                myRoutine.setVisible(false);
                break;

            case "SUPERADMIN":
                dashboard.setVisible(true);
                counseling.setVisible(true);
                sendNotification.setVisible(true);
                classRoutine.setVisible(true);
                //Invisible item to Super admin
                myRoutine.setVisible(false);
                break;

            case "TEACHER":
                counseling.setVisible(true);
                myRoutine.setVisible(true);

                //Invisible item to admin
                dashboard.setVisible(false);
                sendNotification.setVisible(false);
                classRoutine.setVisible(false);
                break;

            case "STUDENT":
                classRoutine.setVisible(true);
                dashboard.setVisible(true);

                //Invisible item to admin
                myRoutine.setVisible(false);
                sendNotification.setVisible(false);
                counseling.setVisible(false);
                break;

        }


    }

    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    private void showMessage(String title, String message) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.custom_desigh_to_show_notification_detail, null);
        dialogBuilder.setView(dialogView);

        TextView titleView = dialogView.findViewById(R.id.custom_alert_notificaton_title);
        TextView messageView=dialogView.findViewById(R.id.custom_slert_notificaton_message);
        ImageView senderPic=dialogView.findViewById(R.id.custom_alert_notification_sender_image);
        titleView.setText(title);
        messageView.setText(message);

        Picasso.with(MainActivity.this)
                .load(R.mipmap.ic_launcher)
                .into(senderPic);

        Button okBtn = dialogView.findViewById(R.id.custom_alert_notification_btn);
        final AlertDialog alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimationUpBottom;

        alertDialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}

