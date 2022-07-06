package com.texasimaginology.ticms.Courses;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment {
    private RecyclerView courseRecyclerView,subjectRecyclerView;
    private ProgressBar progressBar;
    private List<CoursesDto> coursesDtoList=new ArrayList<>();
    private CoursesCustomAdapter coursesCustomAdapter;
    public static MaterialSpinner mSemesterSpinner;
    public static View myView,subjectContainer;
    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_courses_list, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Courses");

        defineViews(view);
        return view;
    }

    private void defineViews(View view) {
        courseRecyclerView=view.findViewById(R.id.recyclerview_courses);
        subjectRecyclerView=view.findViewById(R.id.subject_recyclerview);
        progressBar=view.findViewById(R.id.courseslist_progressbar);
        mSemesterSpinner=view.findViewById(R.id.semester_spinner);
        progressBar.setVisibility(View.VISIBLE);
        myView=view.findViewById(R.id.course_view_container);
        subjectContainer=view.findViewById(R.id.subject_view_container);
        mainWork();
    }

    private void mainWork() {
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<List<CoursesDto>> call = apiInterface.coursesDto(sharedPref.getLong("customerId",0),
                sharedPref.getString("token",""),sharedPref.getLong("loginId",0));
        call.enqueue(new Callback<List<CoursesDto>>() {
            @Override
            public void onResponse(Call<List<CoursesDto>> call, Response<List<CoursesDto>> response) {
                if(response.isSuccessful()){
                    coursesDtoList=response.body();
                    coursesCustomAdapter=new CoursesCustomAdapter(coursesDtoList,R.layout.courses_list_row,getContext(),courseRecyclerView,subjectRecyclerView);
                    progressBar.setVisibility(View.GONE);
                    courseRecyclerView.setAdapter(coursesCustomAdapter);

                }else{
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
            public void onFailure(Call<List<CoursesDto>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


}
