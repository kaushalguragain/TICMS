package com.texasimaginology.ticms.Dashboard;

import java.util.List;

public class TeacherRoutineDTO {
    private String course;
    private String semester;
    private List<RoutineDTO> routinesList;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<RoutineDTO> getRoutinesList() {
        return routinesList;
    }

    public void setRoutinesList(List<RoutineDTO> routinesList) {
        this.routinesList = routinesList;
    }

}
