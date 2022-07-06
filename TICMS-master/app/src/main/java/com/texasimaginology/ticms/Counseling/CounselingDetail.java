package com.texasimaginology.ticms.Counseling;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.texasimaginology.ticms.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class CounselingDetail extends AppCompatActivity {
    TextView counselingFullName;
    TextView counselingEmail;
    TextView counselingPhone;
    EditText negotiatedFee,counseledBy,remark;
    Button submitEdit;
    ImageView show;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    private final String TAG = CounselingDetail.class.getSimpleName();
    public static final int CAMERA_REQUEST = 10;
    private Bitmap profilePic;
    private String counsellingId;
    private String fullName,email,mobileNumber,recomendedBy,schoolName,totalFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling_detail);
        Toolbar Counselingtoolbar = findViewById(R.id.counseling_details_toolbar);
        setSupportActionBar(Counselingtoolbar);
        getSupportActionBar().setTitle("Student Profile");

        progressBar = findViewById(R.id.counselling_progress_bar);
        counselingFullName = findViewById(R.id.counseling_full_name);
        counselingEmail = findViewById(R.id.counseling_email);
        counselingPhone = findViewById(R.id.counseling_phone);
        negotiatedFee=findViewById(R.id.negotiated_fee);
        counseledBy=findViewById(R.id.counseled_by);
        remark=findViewById(R.id.counseling_remark);
        submitEdit=findViewById(R.id.couseling_submit_btn);
        relativeLayout = findViewById(R.id.counseling_details_relative_layout);
        show=findViewById(R.id.counseling_image_view);

        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

        //Get user id from intent
        Intent intent = getIntent();
        counsellingId = intent.getStringExtra("selectedCounsellingId");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getCounsellingDetails(counsellingId);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPicture();
            }
        });

        submitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNewEditedDataToServer();
            }
        });


    }

    private void sendNewEditedDataToServer() {
        CounselingDto counselingDto=new CounselingDto(counseledBy.getText().toString(),email,fullName,mobileNumber,
                Double.valueOf(String.valueOf(negotiatedFee.getText())),recomendedBy,schoolName,Double.valueOf(totalFee),
                remark.getText().toString());

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        profilePic.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
//        byte[] byteArray = baos.toByteArray();
//        String profilePicture=Base64.encodeToString(byteArray, Base64.DEFAULT);


        ApiClient apiClient = new ApiClient(this);
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<CounselingDto> call = apiInterface.editCounsellingInfo(sharedPref.getLong("customerId",0),
                sharedPref.getLong("loginId",0), counselingDto, sharedPref.getString("token",""));
        call.enqueue(new Callback<CounselingDto>() {
            @Override
            public void onResponse(Call<CounselingDto> call, Response<CounselingDto> response) {
                String editSubmitMsg=response.toString();
                Toast.makeText(CounselingDetail.this, editSubmitMsg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, editSubmitMsg+", "+counselingDto.toString());
            }

            @Override
            public void onFailure(Call<CounselingDto> call, Throwable t) {
                Toast.makeText(CounselingDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
            }
        });

    }

    private void uploadPicture() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profilePic.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArray = baos.toByteArray();
        String profilePicture=Base64.encodeToString(byteArray, Base64.DEFAULT);

        ApiClient apiClient = new ApiClient(this);
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<String>call=apiInterface.uplodProfilePic(Long.valueOf(counsellingId),sharedPref.getLong("loginId",0),
                sharedPref.getLong("customerId",0), profilePicture, sharedPref.getString("token",""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String statusMessage=response.toString();
                Toast.makeText(CounselingDetail.this, statusMessage, Toast.LENGTH_SHORT).show();
                Log.d(TAG,statusMessage+", "+profilePicture);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CounselingDetail.this, "Error to connect Server", Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getMessage());
            }
        });
    }

    private void clickPicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    private void getCounsellingDetails(String counsellingId) {
        ApiClient apiClient = new ApiClient(this);
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<CounselingListDto> call = apiInterface.counsellingInfo(sharedPref.getLong("customerId",0),sharedPref.getLong("loginId",0), Long.valueOf(counsellingId),
                sharedPref.getString("token",""));
        call.enqueue(new Callback<CounselingListDto>() {
            @Override
            public void onResponse(Call<CounselingListDto> call, Response<CounselingListDto> response) {
                if(response.isSuccessful()){
                    CounselingListDto counselingDetail = response.body();
                    fullName=CheckError.checkNullString(counselingDetail.getFullName());
                    email=CheckError.checkNullString(counselingDetail.getEmail());
                    mobileNumber=CheckError.checkNullString(counselingDetail.getMobileNumber());
                    schoolName=CheckError.checkNullString(counselingDetail.getSchoolName());
                    recomendedBy=CheckError.checkNullString(counselingDetail.getRecommendedBy());
                    totalFee=CheckError.checkNullString(String.valueOf(counselingDetail.getTotalFee()));
                    counselingFullName.setText(fullName);
                    counselingEmail.setText(email);
                    counselingPhone.setText(mobileNumber);
                    if(null!=counselingDetail.getNegotiatedFee()){
                        negotiatedFee.setText(String.valueOf(counselingDetail.getNegotiatedFee()));
                    }
                    if(null!=counselingDetail.getCounseledBy()){
                        counseledBy.setText(counselingDetail.getCounseledBy());
                    }
                    if(null!=counselingDetail.getRemarks()){
                        remark.setText(counselingDetail.getRemarks());
                    }
                    String picUrl=CheckError.checkNullString(String.valueOf(counselingDetail.getProfilePicture()));

                    if("----".equals(picUrl)){
                        Picasso.with(CounselingDetail.this)
                                .load(R.drawable.user)
                                .into(show);
                    }else{
                        Picasso.with(CounselingDetail.this)
                                .load(picUrl)
                                .into(show);
                    }
                    progressBar.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);


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
            public void onFailure(Call<CounselingListDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.getMessage());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_REQUEST) {
                profilePic = (Bitmap) data.getExtras().get("data");
                show.setImageBitmap(profilePic);
                uploadPicture();
            }
        }
    }
}

