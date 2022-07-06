package com.texasimaginology.ticms.service.RetrofitService;


import com.texasimaginology.ticms.ClassRoutine.RoutineResponseDto;
import com.texasimaginology.ticms.Counseling.CounselingDto;
import com.texasimaginology.ticms.Counseling.CounselingListDto;
import com.texasimaginology.ticms.Courses.CoursesDto;
import com.texasimaginology.ticms.Dashboard.TeacherDashboardDTO;
import com.texasimaginology.ticms.Subjects.SubjectsDto;
import com.texasimaginology.ticms.Login.LoginDto;
import com.texasimaginology.ticms.Logout.LogoutDto;
import com.texasimaginology.ticms.Subjects.CourseDto;
import com.texasimaginology.ticms.Students.StudentDto;
import com.texasimaginology.ticms.Students.StudentListDto;
import com.texasimaginology.ticms.Teachers.TeacherDto;
import com.texasimaginology.ticms.Teachers.TeacherListDto;
import com.texasimaginology.ticms.Notification.TeamDto;
import com.texasimaginology.ticms.Users.UserDto;
import com.texasimaginology.ticms.Users.UserListDto;
import com.texasimaginology.ticms.Notification.PushNotification.NotificationDto;
import com.texasimaginology.ticms.Notification.sendNotification.SendNotificationDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by deepbhai on 9/13/17.
 */

public interface ApiInterface {
final String preFix= "auth/api/v1/";
final String preFixForRoutine= "routine/api/v1/";
    @POST(preFix+"login")
    Call<LoginDto> sendUsernameAndPassword(@Body LoginDto loginDto,
                                           @Header("domain") String domain);

    @POST(preFix+"logout")
    Call<ResponseBody> logout(@Header("userId") int user,
                              @Body LogoutDto logoutDto);

    @GET(preFix+"users")
    Call<List<UserListDto.Content>> listUsers(@Header("loginId") Long loginId,
                                              @Header("customerId") Long customerId,
                                              @Header("token") String token,
                                              @Query("sort") String sort,
                                              @Query("size") String size,
                                              @Query("page") String page,
                                              @Query("search") String search);

    @GET(preFix+"notifications/own")
    Call<NotificationDto> listNotification(@Header("loginId") Long lodinId,
                                           @Header("customerId") Long customerId,
                                           @Header("token") String token);

    @GET(preFix+"students")
    Call<StudentListDto> listStudents(@Header("customerId") Long customerId,
                                      @Header("token") String token,
                                      @Header("userId") Long userId,
                                      @Query("sort") String sort,
                                      @Query("size") String size,
                                      @Query("page") String page,
                                      @Query("search") String search);

    @GET(preFix+"teachers")
    Call<TeacherListDto> listTeachers(@Header("userId") Long userId,
                                      @Header("customerId") Long customerId,
                                      @Query("sort") String sort,
                                      @Query("size") String size,
                                      @Query("page") String page,
                                      @Query("search") String search,
                                      @Header("token") String token);

    @GET(preFix+"students/{studentId}")
    Call<StudentDto> studentInfo(@Header("customerId") Long customerId,
                                 @Path("studentId") Long studentId,
                                 @Header("token") String token,
                                 @Header("userId") Long userId);

    @GET(preFix+"users/{id}")
    Call<UserDto> userInfo(@Header("loginId") Long loginId,
                           @Path("id") Long id,
                           @Header("customerId") Long customerId,
                           @Header("token") String token);

    @GET(preFix+"teachers/{teacherId}")
    Call<TeacherDto> teacherInfo(@Header("userId") Long userId,
                                 @Header("customerId") Long customerId,
                                 @Path("teacherId") Long teacherId,
                                 @Header("token") String token);

    @POST(preFix+"notifications")
    Call<String> sendNotification(@Header("loginId") Long loginId,
                                  @Header("customerId") Long customerId,
                                  @Body SendNotificationDTO sendNotificationDTO,
                                  @Header("token") String token);

    @GET(preFix+"courses")
    Call<List<CourseDto>> courseDto(@Header("customerId") Long customerId,
                                    @Header("token") String token,
                                    @Header("userId") Long userId);

    @GET(preFix+"courses")
    Call<List<CoursesDto>> coursesDto(@Header("customerId") Long customerId,
                                      @Header("token") String token,
                                      @Header("userId") Long userId);

    @GET(preFix+"routines/courses/{courseId}/routines")
    Call<RoutineResponseDto> classRoutineDto(@Header("customerId") Long customerId,
                                                   @Header("loginId") Long loginId,
                                                   @Path("courseId") Long courseId,
                                                   @Header("token") String token);

    @GET(preFix+"teams")
    Call<TeamDto> getTeams(@Header("customerId") Long customerId,
                           @Header("token") String token,
                           @Header("loginId") Long loginId,
                           @Query("page") int page,
                           @Query("size") int size,
                           @Query("sort") String sort,
                           @Query("search") String search
    );


    @GET(preFix+"subjects/{courseId}/{semester}")
    Call<List<SubjectsDto>> getListOfSubjects(@Header("customerId") Long customerId,
                                              @Path("courseId") Long courseId,
                                              @Path("semester") String semester,
                                              @Header("token") String token,
                                              @Header("loginId") Long loginId);

    @POST(preFix+"studentCounsellings")
    Call<CounselingDto> counselling(@Header("userId") Long loginId,
                                    @Header("customerId") Long customerId,
                                    @Header("token") String token,
                                    @Body CounselingDto counselingDto);

    @GET(preFix+"studentCounsellings")
    Call<List<CounselingListDto>> getListOfCounsellings(@Header("customerId") Long customerId,
                                                        @Header("loginId") Long loginId,
                                                        @Header("token") String token);

    @GET(preFix+"studentCounsellings/{id}")
    Call<CounselingListDto> counsellingInfo(@Header("customerId") Long userId,
                                 @Header("loginId") Long customerId,
                                 @Path("id") Long id,
                                 @Header("token") String token);

    @PUT(preFix+"studentCounsellings")
    Call<CounselingDto>editCounsellingInfo(@Header("customerId")Long customerId,
                                           @Header("loginId")Long loginId,
                                           @Body CounselingDto counselingDto,
                                           @Header("token")String token);

    @PUT(preFix+"studentCounsellings/{profileId}/profile-pic-change")
    Call<String>uplodProfilePic(@Path("profileId")Long profileId,
                                @Header("loginId")Long loginId,
                                @Header("customerId")Long customerId,
                                @Body String profilePic,
                                @Header("token")String token);

    @GET(preFixForRoutine+"routines/teachers/{teacherId}")
    Call<TeacherDashboardDTO>getTeacherRoutines(@Path("teacherId")Long teacherId,
                                                      @Header("customerId")Long customerId,
                                                      @Header("loginId")Long loginId,
                                                      @Header("token")String token);
}
