package com.texasimaginology.ticms.Counseling;

/**
 * Created by yubar on 5/25/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CounselingListDto {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("schoolName")
    @Expose
    private String schoolName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("courseId")
    @Expose
    private Long courseId;
    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("customerId")
    @Expose
    private Long customerId;
    @SerializedName("totalFee")
    @Expose
    private Double totalFee;
    @SerializedName("negotiatedFee")
    @Expose
    private Double negotiatedFee;
    @SerializedName("counseledBy")
    @Expose
    private String counseledBy;
    @SerializedName("recommendedBy")
    @Expose
    private String recommendedBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("createdByName")
    @Expose
    private String createdByName;
    @SerializedName("modifiedBy")
    @Expose
    private String modifiedBy;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("remarks")
    @Expose
    private String remarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getNegotiatedFee() {
        return negotiatedFee;
    }

    public void setNegotiatedFee(Double negotiatedFee) {
        this.negotiatedFee = negotiatedFee;
    }

    public String getCounseledBy() {
        return counseledBy;
    }

    public void setCounseledBy(String counseledBy) {
        this.counseledBy = counseledBy;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}