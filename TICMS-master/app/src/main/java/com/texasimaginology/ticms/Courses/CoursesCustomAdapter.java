package com.texasimaginology.ticms.Courses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.texasimaginology.ticms.CheckError;
import com.texasimaginology.ticms.Error.ErrorMessageDto;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.Subjects.SubjectsDto;
import com.texasimaginology.ticms.Subjects.SubjectsDtoAdapter;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.texasimaginology.ticms.Courses.CourseListFragment.mSemesterSpinner;

/**
 * Created by yubar on 5/1/2018.
 */

public class CoursesCustomAdapter extends RecyclerView.Adapter<CoursesCustomAdapter.CoursesDtoHolder> {
    private LayoutInflater inflator;
    private Context context;
    private List<CoursesDto> coursesDtos = Collections.emptyList();
    private int rowLayout;
    private RecyclerView courseRecyclerView,subjectRecyclerView;
    private String getSemester;
    private Integer getCourseId;

    public CoursesCustomAdapter(List<CoursesDto> subjectsDtos, int rowLayout, Context context,
                                RecyclerView courseRecyclerView,RecyclerView subjectRecyclerView) {
        this.context = context;
        this.coursesDtos = subjectsDtos;
        this.rowLayout = rowLayout;
        this.courseRecyclerView=courseRecyclerView;
        this.subjectRecyclerView=subjectRecyclerView;
    }

    @Override
    public CoursesCustomAdapter.CoursesDtoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CoursesCustomAdapter.CoursesDtoHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursesCustomAdapter.CoursesDtoHolder holder, int position) {
        holder.courseName.setText(CheckError.checkNullString(coursesDtos.get(position).getName()));
        holder.courseDescription.setText(CheckError.checkNullString(coursesDtos.get(position).getDescription()));
        holder.courseAffilation.setText(CheckError.checkNullString(coursesDtos.get(position).getAffilation()));

        holder.relativeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListFragment.myView.setVisibility(View.GONE);
                CourseListFragment.subjectContainer.setVisibility(View.VISIBLE);
                getCourseId=coursesDtos.get(position).getId();
                getSemester();
            }
        });

    }

    @Override
    public int getItemCount() {
        return coursesDtos.size();
    }

    public void setFilter(List<CoursesDto> newList){
        coursesDtos = new ArrayList<>();
        coursesDtos.addAll(newList);
        notifyDataSetChanged();
    }

    public class CoursesDtoHolder extends RecyclerView.ViewHolder{
        TextView courseName, courseDescription, courseAffilation;
        View relativeView;
        public CoursesDtoHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_name);
            courseDescription = itemView.findViewById(R.id.course_description);
            courseAffilation = itemView.findViewById(R.id.course_affilation);
            relativeView=itemView.findViewById(R.id.course_row_relativelayout);
        }
    }

    private void getSemester(){
        final String [] semester = {"FIRST","SECOND","THIRD","FOURTH","FIFTH","SIXTH","SEVENTH","EIGHTH"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item, semester);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSemesterSpinner.setAdapter(adapter);

        mSemesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mSemesterSpinner.getSelectedItem()!=null){
                    getSemester = semester[position];
                    Toast.makeText(context, "Selected: "+semester[position], Toast.LENGTH_SHORT).show();
                    getSubjectsList();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getSubjectsList() {
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        ApiClient apiClient = new ApiClient(context);
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPref = ((Activity)context).getSharedPreferences("loginDetails", Context.MODE_PRIVATE);

        Call<List<SubjectsDto>> call = apiInterface.getListOfSubjects(sharedPref.getLong("customerId",0),
                Long.valueOf(getCourseId), getSemester,sharedPref.getString("token",""),sharedPref.getLong("loginId",0));
        call.enqueue(new Callback<List<SubjectsDto>>() {
            @Override
            public void onResponse(Call<List<SubjectsDto>> call, Response<List<SubjectsDto>> response) {
                if(response.isSuccessful()){
                    List<SubjectsDto> subjectsDtos = response.body();
                    SubjectsDtoAdapter subjectsDtoAdapter = new SubjectsDtoAdapter(subjectsDtos, R.layout.course_list_adapter, context);
                    subjectRecyclerView.setAdapter(subjectsDtoAdapter);
                }else{
                    //Maps the error message in ErrorMessageDto
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        ErrorMessageDto errorMessageDto = gson.fromJson(mJson, ErrorMessageDto.class);
                        Toast.makeText(context, errorMessageDto.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SubjectsDto>> call, Throwable t) {
                Toast.makeText(context, R.string.no_response, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
