package com.texasimaginology.ticms.Notification.OfflineNotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.texasimaginology.ticms.Dashboard.NotificationRoutineCheckDTO;
import com.texasimaginology.ticms.Dashboard.RoutineDTO;
import com.texasimaginology.ticms.Dashboard.TeacherDashboardDTO;
import com.texasimaginology.ticms.RoomDatabase.UserLoginResponse.UserLoginResponseEntity;
import com.texasimaginology.ticms.service.RetrofitService.ApiClient;
import com.texasimaginology.ticms.service.RetrofitService.ApiInterface;
import com.texasimaginology.ticms.util.GetLoginInstanceFromDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineNotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        GetLoginInstanceFromDatabase getLoginInstanceFromDatabase= new GetLoginInstanceFromDatabase(this);
        UserLoginResponseEntity loginInstance= getLoginInstanceFromDatabase.getLoginInstance();
        Log.d("In Service: ", loginInstance.getUserName());
        checkAndTriggerNotification(loginInstance);
        return START_STICKY;
    }


    private void checkAndTriggerNotification(UserLoginResponseEntity loginInstance) {
        Log.d("In CheckAndTrigger: ", loginInstance.getUserName());

        ApiClient apiClient = new ApiClient(this);
        ApiInterface apiInterface = apiClient.getRetrofit().create(ApiInterface.class);
        Call<TeacherDashboardDTO> call= apiInterface.getTeacherRoutines(loginInstance.getUserId(),loginInstance.getCustomerId(),
                loginInstance.getLoginId(), loginInstance.getToken());
        call.enqueue(new Callback<TeacherDashboardDTO>() {
            @Override
            public void onResponse(Call<TeacherDashboardDTO> call, Response<TeacherDashboardDTO> response) {
                if(response.isSuccessful()){
                    TeacherDashboardDTO teacherDashboardDTOList=response.body();
                    List<NotificationRoutineCheckDTO> notificationRoutineCheckDTOList= getRoutineListForNotificationCheck(teacherDashboardDTOList.getData().get(0));
                    runScheduler(notificationRoutineCheckDTOList, loginInstance);
                }
            }
            @Override
            public void onFailure(Call<TeacherDashboardDTO> call, Throwable t) {

            }
        });

    }

    private void runScheduler(List<NotificationRoutineCheckDTO> notificationRoutineCheckDTOList, UserLoginResponseEntity loginInstance){
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                Calendar calendar= Calendar.getInstance();
                while(true){
                    for(NotificationRoutineCheckDTO notificationRoutineCheckDTO: notificationRoutineCheckDTOList){
                        Log.d("DAy", notificationRoutineCheckDTO.getDay());
                        Log.d("System day", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                        if(calendar.get(Calendar.DAY_OF_WEEK)==getDayByName(notificationRoutineCheckDTO.getDay())){
                            long routineTime = notificationRoutineCheckDTO.getStartTimeInMilis();
                            long triggerTime=routineTime-10*60*1000; // minute*second*millisecond
                            long currentTime = System.currentTimeMillis();
                            Log.d("Trigger Time: ", String.valueOf(triggerTime));
                            Log.d("System Time: ", String.valueOf(currentTime));
                            if(currentTime <= triggerTime){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    Log.d("Notification service: ", "Triggered");
                                    Log.d("Time: ", notificationRoutineCheckDTO.getStartTimeInMilis().toString());

                                    Intent intent = new Intent(RoutineNotificationService.this,RoutineNotificationReceiver.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Course", notificationRoutineCheckDTO.getCourse());
                                    bundle.putString("Semester", notificationRoutineCheckDTO.getSemester());
                                    bundle.putLong("StartTime", notificationRoutineCheckDTO.getStartTimeInMilis());
                                    bundle.putString("Subject", notificationRoutineCheckDTO.getSubject());
                                    bundle.putString("Teacher", loginInstance.getFirstName());
                                    intent.putExtras(bundle);
                                    PendingIntent broadcast = PendingIntent.getBroadcast
                                            (RoutineNotificationService.this, RoutineNotificationReceiver.NOTIFICATION_ID, intent, 0);
                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,  triggerTime,broadcast);
                                    }else{
                                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,  triggerTime,broadcast);
                                    }
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<NotificationRoutineCheckDTO> getRoutineListForNotificationCheck(TeacherDashboardDTO.Datum teacherDashboardDTOList) {
        List<NotificationRoutineCheckDTO> notificationRoutineCheckDTOList = new ArrayList<>();
        for(TeacherDashboardDTO.Course course: teacherDashboardDTOList.getCourses()){
            for(TeacherDashboardDTO.Semester semester: course.getSemesters()){
                for(RoutineDTO routine: semester.getRoutines()){
                    NotificationRoutineCheckDTO notificationRoutineCheckDTO= new NotificationRoutineCheckDTO();
                    notificationRoutineCheckDTO.setCourse(course.getName());
                    notificationRoutineCheckDTO.setSemester(semester.getValue());
                    notificationRoutineCheckDTO.setDay(routine.getDay());
                    notificationRoutineCheckDTO.setSubject(routine.getSubject());
                    String[] time= routine.getStartTime().split(":");

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(time[0]));
                    calendar.set(Calendar.MINUTE,Integer.valueOf(time[1]));
                    calendar.set(Calendar.DAY_OF_WEEK,getDayByName(routine.getDay()));

                    notificationRoutineCheckDTO.setStartTimeInMilis(calendar.getTimeInMillis());

                    notificationRoutineCheckDTOList.add(notificationRoutineCheckDTO);
                }
            }
        }

        return notificationRoutineCheckDTOList;
    }

    private int getDayByName(String dayName){
        int x=0;
        switch (dayName){
            case "Sunday":
                x= 1;
                break;
            case "Monday":
                x=2;
                break;
            case "Tuesday":
                x=3;
                break;
            case "Wednesday" :
                x= 4;
                break;
            case "Thursday":
                x=5;
                break;
            case "Friday":
                x=6;
                break;
            case "Saturday":
                x=7;
                break;
        }
        return x;
    }
}
