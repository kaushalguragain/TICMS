package com.texasimaginology.ticms.Teachers;

import android.app.ProgressDialog;
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

public class TeacherListFragment extends Fragment implements SearchView.OnQueryTextListener, TeacherDtoAdapter.ClickListener, View.OnClickListener{
    private TeacherDtoAdapter teacherDtoAdapter;
    List<TeacherListDto.Content> teacherListDto;
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        progressBar = view.findViewById(R.id.userlist_progressbar);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Teachers");
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiService =
                apiClient.getRetrofit().create(ApiInterface.class);
        //retrofit2.Call<UserListDto> call = apiService.listUsers("0", "5", "0", "0");
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Call<TeacherListDto> call = apiService.listTeachers(sharedPref.getLong("loginId",0),sharedPref.getLong("customerId",0),"0","5","3","2",sharedPref.getString("token",""));
        call.enqueue(new Callback<TeacherListDto>() {
            @Override
            public void onResponse(Call<TeacherListDto> call, Response<TeacherListDto> response) {
                if (response.isSuccessful()) {
                    List<TeacherListDto.Content> teacherDetails = response.body().getContents();
                    teacherListDto = response.body().getContents();
                    teacherDtoAdapter = new TeacherDtoAdapter(teacherDetails, R.layout.user_list_adapter, getContext());
                    teacherDtoAdapter.setTeacherClickListener(TeacherListFragment.this);
                    recyclerView.setAdapter(teacherDtoAdapter);
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
            public void onFailure(Call<TeacherListDto> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void itemClicked(View v, TeacherListDto.Content teacherlistDto, int position) {
        final ProgressDialog myProgressDialog;
        myProgressDialog = new ProgressDialog(getContext());
        myProgressDialog.setTitle("Loading");
        myProgressDialog.show();
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiService = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<TeacherDto> call = apiService.teacherInfo(sharedPref.getLong("loginId",0),
                sharedPref.getLong("customerId",0), Long.valueOf(teacherlistDto.getId()),
                sharedPref.getString("token",""));
        Log.d("id===",teacherlistDto.getId().toString());
        call.enqueue(new Callback<TeacherDto>() {
            @Override
            public void onResponse(Call<TeacherDto> call, Response<TeacherDto> response) {
                if (response.isSuccessful()) {
                    TeacherDto teacherDetails = response.body();
                    Intent teacherDetailsIntentActivity = new Intent(getActivity().getApplicationContext(), TeacherDetails.class);
                    teacherDetailsIntentActivity.putExtra("firstName", CheckError.checkNullString(teacherDetails.getFirstName()));
                    teacherDetailsIntentActivity.putExtra("lastName",CheckError.checkNullString(teacherDetails.getLastName()));
                    teacherDetailsIntentActivity.putExtra("email",CheckError.checkNullString(teacherDetails.getEmail()));
                    teacherDetailsIntentActivity.putExtra("phone", CheckError.checkNullString(teacherDetails.getPhoneNumber()));
                    teacherDetailsIntentActivity.putExtra("zone",CheckError.checkNullString(teacherDetails.getAddresses().get(0).getZone()));
                    teacherDetailsIntentActivity.putExtra("district", CheckError.checkNullString(teacherDetails.getAddresses().get(0).getDistrict()));
                    teacherDetailsIntentActivity.putExtra("vdc",CheckError.checkNullString(teacherDetails.getAddresses().get(0).getVdc()));
                    teacherDetailsIntentActivity.putExtra("ward", CheckError.checkNullString(String.valueOf(teacherDetails.getAddresses().get(0).getWardNo())));
                    teacherDetailsIntentActivity.putExtra("profilePicture", CheckError.checkNullString(String.valueOf(teacherDetails.getProfilePicture())));
                    startActivity(teacherDetailsIntentActivity);
                    myProgressDialog.hide();
                } else {
                    myProgressDialog.hide();
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
            public void onFailure(Call<TeacherDto> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                myProgressDialog.hide();
            }
        });
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        //By uv to search students
        newText = newText.toLowerCase();
        List<TeacherListDto.Content> newList = new ArrayList<>();
        for (TeacherListDto.Content list : teacherListDto) {
            String studentName = list.getFirstName().toLowerCase();
            String studentId = String.valueOf(list.getId());
            String studentLastName = list.getLastName().toLowerCase();
            if (studentName.contains(newText) || studentId.contains(newText) || studentLastName.contains(newText)) {
                newList.add(list);
            }
        }
        teacherDtoAdapter.setFilter(newList);
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
