package com.texasimaginology.ticms.Teachers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDto {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("gender")
    @Expose
    private String gender;
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
    @SerializedName("createdByName")
    @Expose
    private String createdByName;
    @SerializedName("modifiedByName")
    @Expose
    private String modifiedByName;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getModifiedByName() {
        return modifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
        this.modifiedByName = modifiedByName;
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



//public class TeacherDto {
//
//    @SerializedName("teacher")
//    @Expose
//    private Teacher teacher;
//
//    public Teacher getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(Teacher teacher) {
//        this.teacher = teacher;
//    }
//    public class Teacher {
//
//        @SerializedName("id")
//        @Expose
//        private Integer id;
//        @SerializedName("status")
//        @Expose
//        private String status;
//        @SerializedName("firstName")
//        @Expose
//        private String firstName;
//        @SerializedName("lastName")
//        @Expose
//        private String lastName;
//        @SerializedName("email")
//        @Expose
//        private String email;
//        @SerializedName("phoneNumber")
//        @Expose
//        private String phoneNumber;
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
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
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
//        public String getEmail() {
//            return email;
//        }
//
//        public String getProfilePicture() {
//            return profilePicture;
//        }
//        public void setProfilePicture(String profilePicture) {
//            this.profilePicture = profilePicture;
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
//        public void setEmail(String email) {
//            this.email = email;
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