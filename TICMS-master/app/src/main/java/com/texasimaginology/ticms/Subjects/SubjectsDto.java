package com.texasimaginology.ticms.Subjects;

/**
 * Created by deepbhai on 2/18/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectsDto {

    @SerializedName("subjectName")
    @Expose
    private String subjectName;
    @SerializedName("subjectCode")
    @Expose
    private String subjectCode;
    @SerializedName("subDescription")
    @Expose
    private String subDescription;
    @SerializedName("creditHour")
    @Expose
    private Integer creditHour;
    @SerializedName("fullMarks")
    @Expose
    private Integer fullMarks;
    @SerializedName("passMarks")
    @Expose
    private Integer passMarks;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("alias")
    @Expose
    private String alias;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public Integer getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(Integer creditHour) {
        this.creditHour = creditHour;
    }

    public Integer getFullMarks() {
        return fullMarks;
    }

    public void setFullMarks(Integer fullMarks) {
        this.fullMarks = fullMarks;
    }

    public Integer getPassMarks() {
        return passMarks;
    }

    public void setPassMarks(Integer passMarks) {
        this.passMarks = passMarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}