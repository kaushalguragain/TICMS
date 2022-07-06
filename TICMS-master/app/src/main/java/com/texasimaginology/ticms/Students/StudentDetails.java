package com.texasimaginology.ticms.Students;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.texasimaginology.ticms.CheckError;
import com.texasimaginology.ticms.Error.ErrorMessageDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.Users.UserDetails;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class StudentDetails extends AppCompatActivity {
    TextView studentFullName,gender;
    TextView studentEmail;
    TextView studentPhone;
    TextView addressZone, addressDistrict, addressVdc, addressWard;
    ImageView show;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    private final String TAG = UserDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        Toolbar Studenttoolbar = findViewById(R.id.student_details_toolbar);
        setSupportActionBar(Studenttoolbar);
        getSupportActionBar().setTitle("Student Profile");

        progressBar = findViewById(R.id.progress_bar);
        studentFullName = findViewById(R.id.student_full_name);
        studentEmail = findViewById(R.id.student_email);
        studentPhone = findViewById(R.id.student_phone);
        gender = findViewById(R.id.gender);
        addressZone = findViewById(R.id.zone);
        addressDistrict = findViewById(R.id.district);
        addressVdc = findViewById(R.id.vdc);
        addressWard = findViewById(R.id.ward);
        relativeLayout = findViewById(R.id.student_details_relative_layout);
        show=findViewById(R.id.student_image_view);

        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

        //Get user id from intent
        Intent intent = getIntent();
        String userId = intent.getStringExtra("selectedUserId");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getUserDetails(userId);
    }

    private void getUserDetails(String userId) {
        ApiClient apiClient = new ApiClient(this);
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<StudentDto> call = apiInterface.studentInfo(sharedPref.getLong("customerId",0), Long.valueOf(userId),
                sharedPref.getString("token",""),sharedPref.getLong("loginId",0));
        call.enqueue(new Callback<StudentDto>() {
            @Override
            public void onResponse(Call<StudentDto> call, Response<StudentDto> response) {
                if(response.isSuccessful()){
                    StudentDto studentDto = response.body();
                    studentFullName.setText(CheckError.checkNullString(studentDto.getStudent().getFirstName()) + " " + CheckError.checkNullString(studentDto.getStudent().getLastName()));
                    studentEmail.setText(CheckError.checkNullString(studentDto.getStudent().getEmail()));
                    studentPhone.setText(CheckError.checkNullString(studentDto.getStudent().getPhoneNumber()));
                    gender.setText(CheckError.checkNullString(studentDto.getStudent().getGender()));
                    addressZone.setText(CheckError.checkNullString(studentDto.getStudent().getAddresses().get(0).getZone()));
                    addressDistrict.setText(CheckError.checkNullString(studentDto.getStudent().getAddresses().get(0).getDistrict()));
                    addressVdc.setText(CheckError.checkNullString(studentDto.getStudent().getAddresses().get(0).getVdc()));
                    addressWard.setText(CheckError.checkNullString(String.valueOf(studentDto.getStudent().getAddresses().get(0).getWardNo())));
                    String picUrl=CheckError.checkNullString(String.valueOf(studentDto.getStudent().getProfilePicture()));

                    if("----".equals(picUrl)){
                        Picasso.with(StudentDetails.this)
                                .load(R.drawable.user)
                                .into(show);
                    }else{
                        Picasso.with(StudentDetails.this)
                                .load(picUrl)
                                .into(show);
                    }
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);

                    Log.d("AddressSize", String.valueOf(studentDto.getStudent().getAddresses().get(0).getDistrict()));


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
            public void onFailure(Call<StudentDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
