package com.texasimaginology.ticms.ClassRoutine;

import java.io.Serializable;
import java.util.List;

public class RoutineResponseDto implements Serializable{
    private Long courseId;
    private  String courseName;
    List<SemesterRoutineResponse> semesterRoutineResponse;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<SemesterRoutineResponse> getSemesterRoutineResponse() {
        return semesterRoutineResponse;
    }

    public void setSemesterRoutineResponse(List<SemesterRoutineResponse> semesterRoutineResponse) {
        this.semesterRoutineResponse = semesterRoutineResponse;
    }
}
