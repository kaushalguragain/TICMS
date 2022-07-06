package com.texasimaginology.ticms.Students;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentDto {

    @SerializedName("student")
    @Expose
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public class Student {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("batch")
        @Expose
        private Integer batch;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("middleName")
        @Expose
        private String middleName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("courseId")
        @Expose
        private Integer courseId;
        @SerializedName("rollNumber")
        @Expose
        private String rollNumber;
        @SerializedName("semester")
        @Expose
        private String semester;
        @SerializedName("profilePicture")
        @Expose
        private String profilePicture;
        @SerializedName("addresses")
        @Expose
        private List<Address> addresses = null;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("modifiedDate")
        @Expose
        private String modifiedDate;
        @SerializedName("createdBy")
        @Expose
        private Integer createdBy;
        @SerializedName("modifiedBy")
        @Expose
        private Integer modifiedBy;
        @SerializedName("modifiedByName")
        @Expose
        private String modifiedByName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getBatch() {
            return batch;
        }

        public void setBatch(Integer batch) {
            this.batch = batch;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public void setRollNumber(String rollNumber) {
            this.rollNumber = rollNumber;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
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

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Integer modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedByName() {
            return modifiedByName;
        }

        public void setModifiedByName(String modifiedByName) {
            this.modifiedByName = modifiedByName;
        }

    }

    public class Address {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("zone")
        @Expose
        private String zone;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("vdc")
        @Expose
        private String vdc;
        @SerializedName("wardNo")
        @Expose
        private Integer wardNo;
        @SerializedName("type")
        @Expose
        private String type;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getVdc() {
            return vdc;
        }

        public void setVdc(String vdc) {
            this.vdc = vdc;
        }

        public Integer getWardNo() {
            return wardNo;
        }

        public void setWardNo(Integer wardNo) {
            this.wardNo = wardNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }
}




//
//public class StudentDto {
//
//    @SerializedName("student")
//    @Expose
//    private Student student;
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public class Student {
//
//        @SerializedName("id")
//        @Expose
//        private Integer id;
//        @SerializedName("status")
//        @Expose
//        private String status;
//        @SerializedName("batch")
//        @Expose
//        private Integer batch;
//        @SerializedName("firstName")
//        @Expose
//        private String firstName;
//        @SerializedName("middleName")
//        @Expose
//        private String middleName;
//        @SerializedName("lastName")
//        @Expose
//        private String lastName;
//        @SerializedName("gender")
//        @Expose
//        private String gender;
//        @SerializedName("phoneNumber")
//        @Expose
//        private String phoneNumber;
//        @SerializedName("email")
//        @Expose
//        private String email;
//        @SerializedName("rollNumber")
//        @Expose
//        private Integer rollNumber;
//        @SerializedName("profilePicture")
//        @Expose
//        private String profilePicture;
//        @SerializedName("addresses")
//        @Expose
//        private List<Address> addresses = null;
//        @SerializedName("createdDate")
//        @Expose
//        private String createdDate;
//        @SerializedName("createdBy")
//        @Expose
//        private Integer createdBy;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public Integer getBatch() {
//            return batch;
//        }
//
//        public void setBatch(Integer batch) {
//            this.batch = batch;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getMiddleName() {
//            return middleName;
//        }
//
//        public void setMiddleName(String middleName) {
//            this.middleName = middleName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public String getPhoneNumber() {
//            return phoneNumber;
//        }
//
//        public void setPhoneNumber(String phoneNumber) {
//            this.phoneNumber = phoneNumber;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public Integer getRollNumber() {
//            return rollNumber;
//        }
//
//        public void setRollNumber(Integer rollNumber) {
//            this.rollNumber = rollNumber;
//        }
//
//        public String getProfilePicture() {
//            return profilePicture;
//        }
//
//        public void setProfilePicture(String profilePicture) {
//            this.profilePicture = profilePicture;
//        }
//
//        public List<Address> getAddresses() {
//            return addresses;
//        }
//
//        public void setAddresses(List<Address> addresses) {
//            this.addresses = addresses;
//        }
//
//        public String getCreatedDate() {
//            return createdDate;
//        }
//
//        public void setCreatedDate(String createdDate) {
//            this.createdDate = createdDate;
//        }
//
//        public Integer getCreatedBy() {
//            return createdBy;
//        }
//
//        public void setCreatedBy(Integer createdBy) {
//            this.createdBy = createdBy;
//        }
//
//        public class Address {
//
//            @SerializedName("id")
//            @Expose
//            private Object id;
//            @SerializedName("zone")
//            @Expose
//            private String zone;
//            @SerializedName("district")
//            @Expose
//            private String district;
//            @SerializedName("vdc")
//            @Expose
//            private String vdc;
//            @SerializedName("wardNo")
//            @Expose
//            private Integer wardNo;
//            @SerializedName("type")
//            @Expose
//            private String type;
//
//            public Object getId() {
//                return id;
//            }
//
//            public void setId(Object id) {
//                this.id = id;
//            }
//
//            public String getZone() {
//                return zone;
//            }
//
//            public void setZone(String zone) {
//                this.zone = zone;
//            }
//
//            public String getDistrict() {
//                return district;
//            }
//
//            public void setDistrict(String district) {
//                this.district = district;
//            }
//
//            public String getVdc() {
//                return vdc;
//            }
//
//            public void setVdc(String vdc) {
//                this.vdc = vdc;
//            }
//
//            public Integer getWardNo() {
//                return wardNo;
//            }
//
//            public void setWardNo(Integer wardNo) {
//                this.wardNo = wardNo;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//
//        }
//
//    }
//
//}