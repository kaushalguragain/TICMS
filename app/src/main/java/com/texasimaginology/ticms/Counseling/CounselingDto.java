package com.texasimaginology.ticms.Counseling;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CounselingDto {

    @SerializedName("counseledBy")
    @Expose
    private String counseledBy;
    @SerializedName("courseId")
    @Expose
    private Long courseId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("negotiatedFee")
    @Expose
    private Double negotiatedFee;
    @SerializedName("recommendedBy")
    @Expose
    private String recommendedBy;
    @SerializedName("schoolName")
    @Expose
    private String schoolName;
    @SerializedName("totalFee")
    @Expose
    private Double totalFee;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }



    public CounselingDto(String counseledBy, String email, String fullName,
                         String mobileNumber, Double negotiatedFee, String recommendedBy,
                         String schoolName, Double totalFee,String remarks) {
    }

    public String getCounseledBy() {
        return counseledBy;
    }

    public void setCounseledBy(String counseledBy) {
        this.counseledBy = counseledBy;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Double getNegotiatedFee() {
        return negotiatedFee;
    }

    public void setNegotiatedFee(Double negotiatedFee) {
        this.negotiatedFee = negotiatedFee;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

}
