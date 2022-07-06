package com.texasimaginology.ticms.Notification.PushNotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.texasimaginology.ticms.Error.ErrorMessageDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNotification extends AppCompatActivity implements NotificationAdapter.ClickListener, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener{
    private NotificationAdapter notificationAdapter;
    List<NotificationDto.Notification> notificationDtos;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        defineView();

        //getPendingNotification();
    }
/*
    private void getPendingNotification() {
        String title,message,semester,faculty;
        title=getIntent().getStringExtra("title");
        message=getIntent().getStringExtra("body");
        faculty=getIntent().getStringExtra("faculty");

        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("Subject: "+title+ "\n\n");
        stringBuffer.append(message+"\n\n"+"Thank You"+"\n"+"Texas Int'l College");
        showMessage("To: "+faculty ,stringBuffer.toString());

    }*/

    private void defineView() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerview_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiClient apiClient = new ApiClient(this);
        ApiInterface apiService = apiClient.getRetrofit().create(ApiInterface.class);

        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call <NotificationDto> call = apiService.listNotification(sharedPref.getLong("loginId",0),
                sharedPref.getLong("customerId",0), sharedPref.getString("token",""));
        call.enqueue(new Callback<NotificationDto>() {
            @Override
            public void onResponse(Call<NotificationDto> call, Response<NotificationDto> response) {
                if (response.isSuccessful()) {
                    Log.d("Response=====",response.toString());
                    Log.d("code =====",String.valueOf(response.code()));
                    Log.d("body =====",response.body().toString());
                    notificationDtos = response.body().getNotifications();
                    notificationAdapter=new NotificationAdapter(notificationDtos, R.layout.list_view_notification, getApplicationContext());
                    recyclerView.setAdapter(notificationAdapter);
                    notificationAdapter.setNotificationClickListener(AllNotification.this);
                } else {
                    //Maps the error message in ErrorMessageDto
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {

                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
                        Toast.makeText(AllNotification.this, errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<NotificationDto> call, Throwable t) {
                Log.d("AllNotifiFalilure==",t.getMessage());
                Toast.makeText(AllNotification.this, R.string.no_response, Toast.LENGTH_SHORT).show();
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void itemClicked(View v, NotificationDto.Notification notificationDto, int position) {
//        Intent notificationDetail=new Intent(AllNotification.this,NotificationDetail.class);
//        notificationDetail.putExtra("title",notificationDto.getTitle());
//        notificationDetail.putExtra("message",notificationDto.getMessage());
//        notificationDetail.putExtra("semester",notificationDto.getSemester());
//        notificationDetail.putExtra("type",notificationDto.getType());
//        startActivity(notificationDetail);
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(notificationDto.getMessage()+"\n\nThank You"+"\n"+"Texas Int'l College");
        String picUrl=notificationDto.getSender().getProfilePicture();
        showMessage(notificationDto.getTitle(),stringBuffer.toString(),picUrl);


    }

    private void showMessage(String title, String message,String picUrl) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.custom_desigh_to_show_notification_detail, null);
        dialogBuilder.setView(dialogView);

        TextView titleView = dialogView.findViewById(R.id.custom_alert_notificaton_title);
        TextView messageView=dialogView.findViewById(R.id.custom_slert_notificaton_message);
        ImageView senderPic=dialogView.findViewById(R.id.custom_alert_notification_sender_image);
        titleView.setText(title);
        messageView.setText(message);

        if(picUrl!=null && !picUrl.isEmpty()){
            Picasso.with(AllNotification.this)
                    .load(picUrl)
                    .into(senderPic);
        }
        Button okBtn = dialogView.findViewById(R.id.custom_alert_notification_btn);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationUpBottom;

        alertDialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        defineView();
    }
//    public void showMessage(String title,String message){
//        AlertDialog.Builder builder= new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//
//    }
}
