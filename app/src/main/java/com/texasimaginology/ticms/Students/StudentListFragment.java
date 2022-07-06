package com.texasimaginology.ticms.Students;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

public class StudentListFragment extends Fragment implements SearchView.OnQueryTextListener, StudentDtoAdapter.ClickListener, View.OnClickListener {
    private StudentDtoAdapter studentDtoAdapter;
    List<StudentListDto.Contents> studentListDto;
    StudentListDto studentDetails;
    private SearchView searchView;
    private ProgressBar progressBar;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Students");
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiService = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        Call<StudentListDto> call = apiService.listStudents(sharedPref.getLong("customerId",0),sharedPref.getString("token",""),
                sharedPref.getLong("loginId",0),"0", "5", "3", "2");
        call.enqueue(new Callback<StudentListDto>() {
            @Override
            public void onResponse(Call<StudentListDto> call, Response<StudentListDto> response) {
                if (response.isSuccessful()) {
                    /*List<StudentListDto> studentListDtos;
                    for(StudentListDto x: response.body()){
                        studentListDtos.add(x.setFirstName());
                    }*/
                    List<StudentListDto.Contents> studentDetails = response.body().getContents();
                    studentListDto = response.body().getContents();
                    studentDtoAdapter = new StudentDtoAdapter(studentDetails, R.layout.user_list_adapter, getContext());
                    studentDtoAdapter.setStudentClickListener(StudentListFragment.this);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(studentDtoAdapter);
                } else {
                    //Maps the error message in ErrorMessageDto
                    progressBar.setVisibility(View.GONE);
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
            public void onFailure(Call<StudentListDto> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void itemClicked(View v, StudentListDto.Contents studentlistDto, final int position) {

        String selectedUserId = studentlistDto.getId().toString();
        Intent studentDetailsActivity = new Intent(getContext(), StudentDetails.class);
        studentDetailsActivity.putExtra("selectedUserId", selectedUserId);
        startActivity(studentDetailsActivity);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //By uv to search students
        newText = newText.toLowerCase();
        List<StudentListDto.Contents> newList = new ArrayList<>();
        for (StudentListDto.Contents list : studentListDto) {
            String studentName = list.getFirstName().toLowerCase();
            String studentId = String.valueOf(list.getId());
            String studentLastName = list.getLastName().toLowerCase();
            if (studentName.contains(newText) || studentId.contains(newText) || studentLastName.contains(newText)) {
                newList.add(list);
            }
        }
        studentDtoAdapter.setFilter(newList);
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
