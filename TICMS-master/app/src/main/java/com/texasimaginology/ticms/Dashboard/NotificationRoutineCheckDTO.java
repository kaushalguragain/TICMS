package com.texasimaginology.ticms.Dashboard;

public class NotificationRoutineCheckDTO {
    private String course;
    private String semester;
    private String day;
    private Long startTimeInMilis;
    private String subject;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getStartTimeInMilis() {
        return startTimeInMilis;
    }

    public void setStartTimeInMilis(Long startTimeInMilis) {
        this.startTimeInMilis = startTimeInMilis;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
