package com.texasimaginology.ticms.Counseling;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.SearchView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;
        import android.widget.Toast;
        import com.google.gson.Gson;
        import com.google.gson.JsonElement;
        import com.google.gson.JsonParser;
        import com.texasimaginology.ticms.CheckError;
        import com.texasimaginology.ticms.Error.ErrorMessageDto;
        import com.texasimaginology.ticms.R;
        import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
        import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class Counseling_Fragment extends Fragment implements SearchView.OnQueryTextListener, CounselingDtoAdapter.ClickListener, View.OnClickListener{
    private CounselingDtoAdapter counselingDtoAdapter;
    List<CounselingListDto> counselingListDto;
    private SearchView searchView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_counseling, container, false);
        progressBar = view.findViewById(R.id.counseling_list_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        defineView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.search);
        search.setVisible(true);

        searchView = (SearchView) search.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }
    private void defineView(View view) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Students For Counsel");
        final RecyclerView recyclerView = view.findViewById(R.id.counseling_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiService =
                apiClient.getRetrofit().create(ApiInterface.class);
        //retrofit2.Call<UserListDto> call = apiService.listUsers("0", "5", "0", "0");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Call<List<CounselingListDto>> call = apiService.getListOfCounsellings(sharedPref.getLong("customerId",0),sharedPref.getLong("loginId",0),sharedPref.getString("token",""));
        call.enqueue(new Callback<List<CounselingListDto>>() {
            @Override
            public void onResponse(Call<List<CounselingListDto>> call, Response<List<CounselingListDto>> response) {
                if (response.isSuccessful()) {
                    //List<CounselingListDto> CounselingDetails = response.body().getContents();
                    counselingListDto = response.body();
                    counselingDtoAdapter = new CounselingDtoAdapter(counselingListDto, R.layout.user_list_adapter, getContext());
                    counselingDtoAdapter.setCounselingClickListener(Counseling_Fragment.this);
                    recyclerView.setAdapter(counselingDtoAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    //Maps the error message in ErrorMessageDto
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
                        Toast.makeText(getContext(), errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<CounselingListDto>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                Log.d("Error---",t.getMessage().toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void itemClicked(View v, CounselingListDto counselingListDto, int position) {
//        final ProgressDialog myProgressDialog;
//        myProgressDialog = new ProgressDialog(getContext());
//        myProgressDialog.setTitle("Loading");
//        myProgressDialog.show();
//        ApiClient apiClient = new ApiClient(getContext());
//        ApiInterface apiService = apiClient.getRetrofit().create(ApiInterface.class);
//        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
//
//        Call<CounselingListDto> call = apiService.counsellingInfo(sharedPref.getLong("customerId",0),
//                sharedPref.getLong("loginId",0), Long.valueOf(counselingListDto.getId()),
//                sharedPref.getString("token",""));
//        call.enqueue(new Callback<CounselingListDto>() {
//            @Override
//            public void onResponse(Call<CounselingListDto> call, Response<CounselingListDto> response) {
//                if (response.isSuccessful()) {
//                    CounselingListDto counselingDetails = response.body();
//                    Intent counselingDetailsIntentActivity = new Intent(getActivity().getApplicationContext(), CounselingDetail.class);
//                    counselingDetailsIntentActivity.putExtra("fullName", CheckError.checkNullString(counselingDetails.getFullName()));
//                    counselingDetailsIntentActivity.putExtra("email",CheckError.checkNullString(counselingDetails.getEmail()));
//                    counselingDetailsIntentActivity.putExtra("phone", CheckError.checkNullString(counselingDetails.getMobileNumber()));
//                    counselingDetailsIntentActivity.putExtra("courseId",CheckError.checkNullString(counselingDetails.getCourseId().toString()));
//                    counselingDetailsIntentActivity.putExtra("courseName", CheckError.checkNullString(counselingDetails.getCourseName()));
//                    counselingDetailsIntentActivity.putExtra("totalFee",CheckError.checkNullString(counselingDetails.getTotalFee().toString()));
//                    counselingDetailsIntentActivity.putExtra("negotiatedFee", CheckError.checkNullString(String.valueOf(counselingDetails.getNegotiatedFee())));
//                    counselingDetailsIntentActivity.putExtra("counseledBy", CheckError.checkNullString(String.valueOf(counselingDetails.getCounseledBy())));
//                    counselingDetailsIntentActivity.putExtra("schoolName", CheckError.checkNullString(String.valueOf(counselingDetails.getSchoolName())));
//                    counselingDetailsIntentActivity.putExtra("selectedCounsellingId",CheckError.checkNullString(String.valueOf(counselingDetails.getId())));
//                    startActivity(counselingDetailsIntentActivity);
//                    myProgressDialog.hide();
//                } else {
//                    myProgressDialog.hide();
//                    //Maps the error message in ErrorMessageDto
//                    JsonParser parser = new JsonParser();
//                    JsonElement mJson = null;
//                    try {
//                        mJson = parser.parse(response.errorBody().string());
//                        Gson gson = new Gson();
//                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
//                        Toast.makeText(getContext(), errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CounselingListDto> call, Throwable t) {
//                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
//                myProgressDialog.hide();
//            }
//        });

        Intent counselingDetailsIntentActivity = new Intent(getActivity().getApplicationContext(), CounselingDetail.class);
        counselingDetailsIntentActivity.putExtra("selectedCounsellingId",CheckError.checkNullString(String.valueOf(counselingListDto.getId())));
        startActivity(counselingDetailsIntentActivity);
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        //By uv to search students
        newText = newText.toLowerCase();
        List<CounselingListDto> newList = new ArrayList<>();
        for (CounselingListDto list : counselingListDto) {
            String studentName = list.getFullName().toLowerCase();
            String studentId = String.valueOf(list.getId());
            if (studentName.contains(newText) || studentId.contains(newText)) {
                newList.add(list);
            }
        }
        counselingDtoAdapter.setFilter(newList);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public void onClick(View v) {

    }
}










//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//import com.texasimaginology.ticms.Error.ErrorMessageDto;
//import com.texasimaginology.ticms.R;
//import com.texasimaginology.ticms.Subjects.CourseDto;
//import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
//import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import fr.ganfra.materialspinner.MaterialSpinner;
//import id.zelory.compressor.Compressor;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.app.Activity.RESULT_OK;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class Counseling_Fragement extends Fragment {
////    public static final int CAMERA_REQUEST = 10;
////    private EditText cfullname, cemail, cphone, cschoolname, ccounseledby, ctotalfee, cnegotiatedfee, crecommendedBy;
////    private Button csummit;
////    private CircleImageView imageView;
////    private Spinner materialSpinner;
////    //     private String getSemester;
//////     private int getCourseID;
////    ArrayList<CharSequence> courses = new ArrayList<>(Collections.EMPTY_LIST);
////    ArrayList<String> courseId = new ArrayList<>(Collections.EMPTY_LIST);
////
////
//////    private Bitmap thumb_bitmap =null;
//    private RecyclerView recyclerView;
//    public Counseling_Fragement() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_counseling, container, false);
//        init(view);
//
//        return view;
//
//
//    }
//
//
//    private void init(View view) {
//        recyclerView=view.findViewById(R.id.counseling_recycler_view);
////        cfullname = view.findViewById(R.id.edit_fullname);
////        cphone = view.findViewById(R.id.edit_phone);
////        cemail = view.findViewById(R.id.edit_email);
////        cemail = view.findViewById(R.id.edit_email);
////        cschoolname = view.findViewById(R.id.edit_schoolname);
////        ccounseledby = view.findViewById(R.id.edit_counseledby);
////        cnegotiatedfee = view.findViewById(R.id.edit_negotiatefee);
////        ctotalfee = view.findViewById(R.id.edit_totalfee);
////        csummit = view.findViewById(R.id.btnsummit);
////        crecommendedBy = view.findViewById(R.id.edit_recommendedBy);
////
////        imageView = view.findViewById(R.id.Counseling_ImageView);
////        materialSpinner = view.findViewById(R.id.counseling_selectSpinner);
////        getCourse();
//
////
////        imageView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(cameraIntent, CAMERA_REQUEST);
////
////            }
////
////        });
//
//
////       imageView.setOnClickListener(new View.OnClickListener() {
////           @Override
////           public void onClick(View v) {
////               selectImage();
////           }
////       });
//
//
////        csummit.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String fullName = cfullname.getText().toString();
////                String email = cemail.getText().toString();
////                String mobileNumber = cphone.getText().toString();
////                String recommendedBy = crecommendedBy.getText().toString();
////                String schoolName = cschoolname.getText().toString();
////                double totalFee = Double.parseDouble((ctotalfee.getText().toString()));
////                String counseledBy = ccounseledby.getText().toString();
////                double negotiatedFee = Double.parseDouble((cnegotiatedfee.getText().toString()));
////
////                Log.d("SelectedItem", materialSpinner.getSelectedItem().toString());
////
////
////                Log.d("fullname", fullName);
////
////                CounselingDto counselingDto = new CounselingDto(counseledBy, email, fullName, mobileNumber, negotiatedFee, recommendedBy, schoolName, totalFee);
////
////                SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
////
////                //Calling Api using Retrofit for Login
////                ApiInterface apiInterface =
////                        ApiClient.getClient().create(ApiInterface.class);
////                Call<CounselingDto> call = apiInterface.counselling(sharedPref.getLong("loginId", 0), sharedPref.getLong("customerId", 0),
////                        sharedPref.getString("token", ""), counselingDto);
////                call.enqueue(new Callback<CounselingDto>() {
////                    @Override
////                    public void onResponse(Call<CounselingDto> call, Response<CounselingDto> response) {
////                        if (response.isSuccessful()) {
////                            Toast.makeText(getContext(), "task sucess", Toast.LENGTH_SHORT).show();
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(Call<CounselingDto> call, Throwable t) {
////                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
////                    }
////                });
////
////
////            }
////        });
////
//   }
//    
//   
////    public void getCourse() {
////        ApiClient apiClient = new ApiClient(getContext());
////        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
////        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
////        Call<List<CourseDto>> call = apiInterface.courseDto(sharedPref.getLong("customerId", 0),
////                sharedPref.getString("token", ""), sharedPref.getLong("loginId", 0));
////        call.enqueue(new Callback<List<CourseDto>>() {
////            @Override
////            public void onResponse(Call<List<CourseDto>> call, Response<List<CourseDto>> response) {
////                if (response.isSuccessful()) {
////
////                    for (CourseDto x : response.body()) {
////                        courses.add(x.getName());
////                        courseId.add(String.valueOf(x.getId()));
////                    }
////
////
////                } else {
////                    //Maps the error message in ErrorMessageDto
////                    JsonParser parser = new JsonParser();
////                    JsonElement mJson = null;
////                    try {
////                        mJson = parser.parse(response.errorBody().string());
////                        Gson gson = new Gson();
////                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
////                        Toast.makeText(getContext(), errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
////                    } catch (IOException ex) {
////                        ex.printStackTrace();
////                    }
////                }
////
////            }
////
////            @Override
////            public void onFailure(Call<List<CourseDto>> call, Throwable t) {
////                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
////
////            }
////        });
////
////        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.
////                R.layout.simple_spinner_item, courses);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        materialSpinner.setAdapter(adapter);
////
////
////
////    }
//
//
////
////    public void selectImage() {
////        CropImage.activity()
////                .setGuidelines(CropImageView.Guidelines.ON)
////                .setAspectRatio(1, 1)
////                .start(getActivity());
////        Log.d("slectImage", "Iam inside SelectImage");
//
////    }
//
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (resultCode == RESULT_OK) {
////
////            if (requestCode == CAMERA_REQUEST) {
////                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
////                imageView.setImageBitmap(cameraImage);
////
////
////            }
////        }
////    }
////}
//
//
////        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
////            CropImage.ActivityResult result = CropImage.getActivityResult(data);
////            if (resultCode == RESULT_OK) {
////                final Uri resultUri = result.getUri();
////
////                File croppedImageUri = new File(resultUri.getPath());
////
////                try {
////                    thumb_bitmap = new Compressor(getContext())
////                            .compressToBitmap(croppedImageUri);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
////                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();
////
////                //in thumb_byte there will be your selected image
////                Log.d("Image", "Thumb_bitmap:::::" + thumb_bitmap);
////                imageView.setImageURI(resultUri);
////
//////                if(croppedImageUri.exists()){
//////
//////                    Bitmap myBitmap = BitmapFactory.decodeFile(croppedImageUri.getAbsolutePath());
//////
////////                    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
//////
//////                    imageView.setImageBitmap(myBitmap);
//////
//////                }
////
////            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
////                Exception error = result.getError();
////            }
////        }
////    }
//
//
////        }
//

 //   }







