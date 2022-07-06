package com.texasimaginology.ticms.Dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDashboardDTO {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Semester {

        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("routines")
        @Expose
        private List<RoutineDTO> routines = null;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<RoutineDTO> getRoutines() {
            return routines;
        }

        public void setRoutines(List<RoutineDTO> routines) {
            this.routines = routines;
        }

    }

    public class Datum {

        @SerializedName("courses")
        @Expose
        private List<Course> courses = null;

        public List<Course> getCourses() {
            return courses;
        }

        public void setCourses(List<Course> courses) {
            this.courses = courses;
        }

    }

    public class Course {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("semesters")
        @Expose
        private List<Semester> semesters = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public List<Semester> getSemesters() {
            return semesters;
        }

        public void setSemesters(List<Semester> semesters) {
            this.semesters = semesters;
        }

    }
}