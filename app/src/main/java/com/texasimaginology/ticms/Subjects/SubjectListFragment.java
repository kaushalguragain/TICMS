package com.texasimaginology.ticms.Subjects;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectListFragment extends Fragment /*implements SearchView.OnQueryTextListener*/{
    private SearchView searchView;
    private SubjectsDtoAdapter subjectsDtoAdapter;
    List<CourseDto> courseDtos;
    private ProgressBar progressBar;
    private int getCourseId;
    private String getSemester;
    View myView;
    private TextView labelCourse;
    ArrayList<CharSequence> courses = new ArrayList<>(Collections.EMPTY_LIST);
    ArrayList<String> courseId = new ArrayList<>(Collections.EMPTY_LIST);
    MaterialSpinner mSelectCourse, mSelectSemester;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);
        progressBar = view.findViewById(R.id.courselist_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        mSelectCourse = view.findViewById(R.id.select_course);
        mSelectSemester = view.findViewById(R.id.select_semester);
        labelCourse = view.findViewById(R.id.label_courses);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Subjects");
        labelCourse.setVisibility(View.GONE);
        mSelectCourse.setVisibility(View.GONE);
        mSelectSemester.setVisibility(View.GONE);
        getCourse();
        //defineView(view);

        myView = view;
        return view;
    }

    /*private void defineView(View view) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Courses");

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CoursesDto>> call = apiInterface.courseDto();
        call.enqueue(new Callback<List<CoursesDto>>() {
            @Override
            public void onResponse(Call<List<CoursesDto>> call, Response<List<CoursesDto>> response) {
                if(response.isSuccessful()){
                    List<CoursesDto> courseDtoList = response.body();
                    courseDtos = response.body();
                    subjectsDtoAdapter = new SubjectsDtoAdapter(courseDtoList, R.layout.course_list_adapter, getContext());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(subjectsDtoAdapter);
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
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        /*MenuItem search = menu.findItem(R.id.search);
        search.setVisible(false);

        searchView = (SearchView) search.getActionView();
        searchView.setSubmitButtonEnabled(true);*/
        //searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onQueryTextSubmit(String newText) {
        newText = newText.toLowerCase();
        List<SubjectsDto> newList = new ArrayList<>();
        for (CoursesDto list : courseDtos) {
            String courseName = list.getName().toLowerCase();
            String courseDescription = list.getDescription().toLowerCase();
            String courseAffiliation = list.getAffilation().toLowerCase();
            if (courseName.contains(newText) || courseDescription.contains(newText) || courseAffiliation.contains(newText)) {
                newList.add(list);
            }
        }
        subjectsDtoAdapter.setFilter(newList);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<SubjectsDto> newList = new ArrayList<>();
        for (CoursesDto list : courseDtos) {
            String courseName = list.getName().toLowerCase();
            String courseDescription = list.getDescription().toLowerCase();
            String courseAffiliation = list.getAffilation().toLowerCase();
            if (courseName.contains(newText) || courseDescription.contains(newText) || courseAffiliation.contains(newText)) {
                newList.add(list);
            }
        }
        subjectsDtoAdapter.setFilter(newList);
        return false;
    }*/

    private void getCourse() {
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<List<CourseDto>> call = apiInterface.courseDto(sharedPref.getLong("customerId",0),
                sharedPref.getString("token",""),sharedPref.getLong("loginId",0));
        call.enqueue(new Callback<List<CourseDto>>() {
            @Override
            public void onResponse(Call<List<CourseDto>> call, Response<List<CourseDto>> response) {
                if (response.isSuccessful()) {

                    for (CourseDto x : response.body()) {
                        Log.d("ParsedCourseName", response.body().get(0).getName());
                        courses.add(x.getName());
                        courseId.add(String.valueOf(x.getId()));
                    }
                    mSelectCourse.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
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
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<CourseDto>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.
                R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectCourse.setAdapter(adapter);


        //Setting Click listener on Faculty Items
        mSelectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mSelectCourse.getSelectedItem() != null) {
                    getCourseId = Integer.parseInt(courseId.get(position));
                    Toast.makeText(getContext(), "Course ID: " + courseId.get(position) + " Course Name:" + mSelectCourse.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    mSelectSemester.setVisibility(View.VISIBLE);
                    getSemester();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectSemester.setVisibility(View.GONE);
            }
        });
    }

    private void getSemester(){
        final String [] semester = {"FIRST","SECOND","THIRD","FOURTH","FIFTH","SIXTH","SEVENTH","EIGHTH"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_item, semester);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectSemester.setAdapter(adapter);

        mSelectSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mSelectSemester.getSelectedItem()!=null){
                    getSemester = semester[position];
                    Toast.makeText(getContext(), "Selected: "+semester[position], Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    getSubjectsList();
                    labelCourse.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSubjectsList() {


        final RecyclerView recyclerView = myView.findViewById(R.id.recyclerview_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<List<SubjectsDto>> call = apiInterface.getListOfSubjects(sharedPref.getLong("customerId",0),
                Long.valueOf(getCourseId), getSemester,sharedPref.getString("token",""),sharedPref.getLong("loginId",0));
        call.enqueue(new Callback<List<SubjectsDto>>() {
            @Override
            public void onResponse(Call<List<SubjectsDto>> call, Response<List<SubjectsDto>> response) {
                if(response.isSuccessful()){
                    List<SubjectsDto> subjectsDtos = response.body();
                    subjectsDtoAdapter = new SubjectsDtoAdapter(subjectsDtos, R.layout.course_list_adapter, getContext());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(subjectsDtoAdapter);
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
            public void onFailure(Call<List<SubjectsDto>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
