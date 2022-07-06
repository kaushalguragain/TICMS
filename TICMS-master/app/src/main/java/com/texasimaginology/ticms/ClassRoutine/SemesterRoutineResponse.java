package com.texasimaginology.ticms.ClassRoutine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SemesterRoutineResponse  implements Serializable {
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("routines")
    @Expose
    private List<RoutineDto> routines = null;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<RoutineDto> getRoutines() {
        return routines;
    }

    public void setRoutines(List<RoutineDto> routines) {
        this.routines = routines;
    }
}


