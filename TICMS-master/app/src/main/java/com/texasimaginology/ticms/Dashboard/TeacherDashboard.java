package com.texasimaginology.ticms.Dashboard;


import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.texasimaginology.ticms.MainFragment;
import com.texasimaginology.ticms.Notification.OfflineNotification.RoutineNotificationReceiver;
import com.texasimaginology.ticms.Notification.OfflineNotification.RoutineNotificationService;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;
import com.texasimaginology.ticms.util.LoginChecker;
import com.texasimaginology.ticms.util.NavmenuHideShow;
import com.texasimaginology.ticms.util.NotificationChannelCreator;
import com.texasimaginology.ticms.util.SharedPreferencesUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherDashboard extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvRoutine;
    private TeacherDashboardAdapter teacherDashboardAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;
    public TeacherDashboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_teacher_dashboard, container, false);
        rvRoutine=view.findViewById(R.id.rv_routine_container);
        swipeRefreshLayout= view.findViewById(R.id.swiperefresh);
        navigationView= getActivity().findViewById(R.id.nav_view);
        //Create Channel for Notification for android version equal or greater than Oreo
        NotificationChannelCreator.createNotificationChannel(getContext());
        if(LoginChecker.IsLoggedIn(getContext())){
            SharedPreferences sharedPref = getContext().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
            NavmenuHideShow.showHideNavMenuAccordingToLoginRole(navigationView,sharedPref.getString("loginType",""));
        }else {
            Fragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction ft= getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        callServerForRoutine();
        startRotineNotificationServie();
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    private void callServerForRoutine() {
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
        ApiClient apiClient = new ApiClient(getContext());
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        Call<TeacherDashboardDTO> call= apiInterface.getTeacherRoutines(SharedPreferencesUtil.getUserId(getActivity()),
                SharedPreferencesUtil.getCustomerId(),SharedPreferencesUtil.getLoginId(),
                SharedPreferencesUtil.getToken());
        call.enqueue(new Callback<TeacherDashboardDTO>() {
            @Override
            public void onResponse(Call<TeacherDashboardDTO> call, Response<TeacherDashboardDTO> response) {
                if(response.isSuccessful()){
                    TeacherDashboardDTO teacherDashboardDTOList=response.body();
                    teacherDashboardAdapter= new TeacherDashboardAdapter(getContext(),loadDataSetForAdapter(teacherDashboardDTOList.getData().get(0)));
                    rvRoutine.setAdapter(teacherDashboardAdapter);
                    rvRoutine.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                } else {
                    Log.d("Status: ", String.valueOf(response.code()));
                    Log.d("Message: ",response.message());
                }

                //To hide swipeRefresh loader
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TeacherDashboardDTO> call, Throwable t) {
                Toast.makeText(getContext(), "OnFailure ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<TeacherRoutineDTO> loadDataSetForAdapter(TeacherDashboardDTO.Datum teacherDashboardDTOList) {
        List<TeacherRoutineDTO> teacherRoutineDTOS = new ArrayList<>();
            for(TeacherDashboardDTO.Course course: teacherDashboardDTOList.getCourses()){
                for(TeacherDashboardDTO.Semester semester: course.getSemesters()){
                    TeacherRoutineDTO teacherRoutineDTO = new TeacherRoutineDTO();
                    List<RoutineDTO> routinesList= new ArrayList<>();
                    for(RoutineDTO routine: semester.getRoutines()){
                        teacherRoutineDTO.setCourse(course.getName());
                        teacherRoutineDTO.setSemester(semester.getValue());

                        RoutineDTO teacherRoutine= new RoutineDTO();
                        teacherRoutine.setStartTime(routine.getStartTime());
                        teacherRoutine.setEndTime(routine.getEndTime());
                        teacherRoutine.setDay(routine.getDay());
                        teacherRoutine.setSubject(routine.getSubject());
                        routinesList.add(teacherRoutine);
                        teacherRoutineDTO.setRoutinesList(routinesList);
                    }
                    teacherRoutineDTOS.add(teacherRoutineDTO);
                }
            }
        Log.d("list length:: ", String.valueOf(teacherRoutineDTOS.size()));
        return teacherRoutineDTOS;
    }

    private void startRotineNotificationServie(){
        Intent serviceIntent = new Intent(getContext(), RoutineNotificationService.class);
        getContext().startService(serviceIntent);

    }

    @Override
    public void onStop() {
        super.onStop();
//        startRotineNotificationServie();
    }

    @Override
    public void onRefresh() {
        callServerForRoutine();
        //To show swipeRefresh loader icon
        swipeRefreshLayout.setRefreshing(true);
    }

}
