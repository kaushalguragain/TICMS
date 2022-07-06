package com.texasimaginology.ticms.Notification.sendNotification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.texasimaginology.ticms.Error.ErrorMessageDto;
import com.texasimaginology.ticms.MainActivity;
import com.texasimaginology.ticms.Notification.TeamDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotificationActivity extends AppCompatActivity {

    MaterialSpinner selectTeam,selectSemester;
    //Button sendNotification;
    EditText message,titleOfNotification;
    String selectedFaculty,selectedSemester,messageTobeSent,title;
    FloatingActionButton myFab;
    SendNotificationDTO sendNotificationDTO;
    ProgressBar progressBar;
    private final String TAG=SendNotificationActivity.class.getSimpleName();
    ArrayList<CharSequence> teams = new ArrayList<>(Collections.EMPTY_LIST);
    ArrayList<String> teamid = new ArrayList<>(Collections.EMPTY_LIST);
    Long selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Send Notification");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        //sendNotificationDTO = new SendNotificationDTO();

        defineView();
        getTeamFromServer();

    }

    private void getTeamFromServer() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<TeamDto> call= apiInterface.getTeams(sharedPref.getLong("customerId",0),
                sharedPref.getString("token",""),sharedPref.getLong("loginId",0),20,0,"id,asc", "1");
        call.enqueue(new Callback<TeamDto>() {
            @Override
            public void onResponse(Call<TeamDto> call, Response<TeamDto> response) {
                if(response.isSuccessful()){
                    Log.d("IamInside","Response is Sucessful");
                    for(TeamDto.Content x: response.body().getContents()){
                        teams.add(x.getName());
                        Log.d("ParsedTeamName", x.getName());
                        teamid.add(x.getId().toString());
                    }
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.GONE);
                    //Maps the error message in ErrorMessageDto
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
                        Toast.makeText(getApplicationContext(), errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamDto> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, teams);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTeam.setAdapter(adapter);

        selectTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(selectTeam.getSelectedItem() != null){
                    selectedTeamId = Long.parseLong(teamid.get(position));
//                    sendNotificationDTO.setTeamId(selectedTeamId);
                    Toast.makeText(SendNotificationActivity.this, "TeamId:" + teamid.get(position) + " TeamName: " + teams.get(position), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //getSemester();
    }

   /* private void getSemester() {
        final String [] semester = {"FIRST","SECOND","THIRD","FOURTH","FIFTH","SIXTH","SEVENTH","EIGHTH"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, semester);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectSemester.setAdapter(adapter);

        selectSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(selectSemester.getSelectedItem()!=null){
                    Toast.makeText(getApplicationContext(), "Selected: "+semester[position], Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

    private void defineView(){
        selectTeam =findViewById(R.id.select_team);
        selectSemester=findViewById(R.id.select_semester);
       // sendNotification=findViewById(R.id.send_notification);
        titleOfNotification=findViewById(R.id.title_of_notification);
        message=findViewById(R.id.your_message);
         myFab= findViewById(R.id.fab);

         myFab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 validate();
             }
         });


//        sendNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                validate();
//
//            }
//        });
    }
    private void sendPushNotification(){
        //Calling Api using Retrofit for Login
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("loginDetails" , Context.MODE_PRIVATE);

        Call<String> call = apiService.sendNotification(sharedPreferences.getLong("loginId", 0),
                sharedPreferences.getLong("customerId", 0),sendNotificationDTO,sharedPreferences.getString("token",""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String message = response.body();
                }
                else{
                    Toast.makeText(SendNotificationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SendNotificationActivity.this, "Sent :)", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SendNotificationActivity.this, MainActivity.class));
                finish();
                Log.d("fail", t.getMessage());
            }
        });
    }
    private void validate(){
        messageTobeSent=message.getText().toString();
        selectedFaculty= selectTeam.getSelectedItem().toString();
        //selectedSemester=selectSemester.getSelectedItem().toString();
        title=titleOfNotification.getText().toString();

        if(TextUtils.isEmpty(messageTobeSent)){
            message.setError("Required");
        }else if(TextUtils.isEmpty(title)){
            titleOfNotification.setError("Required");
        }
        else if(TextUtils.isEmpty(selectedFaculty)){
            Toast.makeText(this, "Select the faculty", Toast.LENGTH_SHORT).show();
        }/*else if(TextUtils.isEmpty(selectedSemester)){
            Toast.makeText(this, "select the semester", Toast.LENGTH_SHORT).show();
        }*/else {
//            sendNotificationDTO.setId(String.valueOf(SharedPreferencesUtil.getUserId(this)));
//            sendNotificationDTO.setMessage(messageTobeSent);
//            sendNotificationDTO.setTitle(title);
            SharedPreferences sharedPreferences = getSharedPreferences("loginDetails" , Context.MODE_PRIVATE);
           List<Long>teamIds=new ArrayList<>();
           teamIds.add(selectedTeamId);
            sendNotificationDTO=new SendNotificationDTO(String.valueOf(sharedPreferences.getLong("loginId",0)),teamIds,title,messageTobeSent,"CSIT");
            Log.d(TAG,"selected faculty is:"+ selectTeam);
            Log.d(TAG,"selected semester is:"+selectSemester);
            sendPushNotification();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
