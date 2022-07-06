package com.texasimaginology.ticms.ClassRoutine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.texasimaginology.ticms.Error.ErrorMessageDto;
import com.texasimaginology.ticms.Subjects.CourseDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClassRoutineFragment extends Fragment {
    TableView<List<RoutineDto>> tableView;
    ProgressBar progressBar;
    MaterialSpinner mSelectCourse; // mSelectSemester;
    ArrayList<CharSequence> courses = new ArrayList<>(Collections.EMPTY_LIST);
    ArrayList<String> courseId = new ArrayList<>(Collections.EMPTY_LIST);
    private int getCourseId;

    private int rowLayout;
    private String getcourseName;
    View myView;


    private static final String[] TABLE_HEADERS = { "Time", "Sun", "Mon", "Tue","Wed","Thu","Fri","Sat" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_routine, container, false);
        mSelectCourse = view.findViewById(R.id.select_course);
        progressBar = view.findViewById(R.id.progress_bar);
        tableView = view.findViewById(R.id.tableView);


        progressBar.setVisibility(View.VISIBLE);
//        init(view);
        tableView.setVisibility(view.GONE);
        myView = view;
        getCourse();
        return view;

    }


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
//                    mSelectCourse.setVisibility(View.VISIBLE);// check
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


        //Setting Click listener on Faculty Items checked
        mSelectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mSelectCourse.getSelectedItem() != null) {
                    getCourseId = Integer.parseInt(courseId.get(position));
                    Toast.makeText(getContext(), "Course ID: " + courseId.get(position) + " Course Name:" + mSelectCourse.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    mSelectCourse.setVisibility(View.VISIBLE);
                    displayRoutine();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectCourse.setVisibility(View.GONE);
            }
        });
    }

    private void displayRoutine(){
        progressBar.setVisibility(View.VISIBLE);
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<RoutineResponseDto> call = apiInterface.classRoutineDto( sharedPref.getLong("customerId",0),
                sharedPref.getLong("loginId",0), Long.valueOf(getCourseId),sharedPref.getString("token",""));
        call.enqueue(new Callback<RoutineResponseDto>() {
            @Override
            public void onResponse(Call<RoutineResponseDto> call, Response<RoutineResponseDto> response) {
                if(response.isSuccessful()){
//                    List<RoutineResponseDto> routineResponseDtos =getSemester
                    final int COLUMN_WIDTH = 70;
                    RoutineResponseDto classroutines = response.body();
                    List<SemesterRoutineResponse> semRoutines = classroutines.getSemesterRoutineResponse();
                    if(semRoutines != null){
                        for(SemesterRoutineResponse sr : semRoutines){
                            String semester = sr.getSemester();
                            List<RoutineDto> routineDtoList = sr.getRoutines();
                            if(routineDtoList != null){
                                for(RoutineDto rd : routineDtoList){
                                    TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(getContext(), 8, 90);
                                    columnModel.setColumnWidth(0, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(1, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(2, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(3, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(4, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(5, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(6, COLUMN_WIDTH);
                                    columnModel.setColumnWidth(7, COLUMN_WIDTH);
                                    tableView.setColumnModel(columnModel);
                                    tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getContext(), TABLE_HEADERS));
                                }
                            }
                            TableDataAdapter tableDataAdapter = new ClassRoutineAdapter(getActivity(), routineDtoList);
                            tableView.setDataAdapter(tableDataAdapter);
                            progressBar.setVisibility(View.GONE);
                            tableView.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
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
            public void onFailure(Call<RoutineResponseDto> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), R.string.no_response, Toast.LENGTH_SHORT).show();
            }
        });



    }
}

