package com.texasimaginology.ticms.Teachers;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherListDto implements Parcelable {

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;
    public final static Creator<TeacherListDto> CREATOR = new Creator<TeacherListDto>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TeacherListDto createFromParcel(Parcel in) {
            TeacherListDto instance = new TeacherListDto();
            instance.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.contents, (TeacherListDto.Content.class.getClassLoader()));
            return instance;
        }

        public TeacherListDto[] newArray(int size) {
            return (new TeacherListDto[size]);
        }

    };

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(size);
        dest.writeList(contents);
    }

    public int describeContents() {
        return 0;
    }

    public static class Content implements Parcelable {

        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("userRole")
        @Expose
        private String userRole;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("profilePicture")
        @Expose
        private String profilePicture;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;

        public static final Creator<Content> CREATOR = new Creator<Content>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Content createFromParcel(Parcel in) {
                Content instance = new Content();
                instance.firstName = ((String) in.readValue((String.class.getClassLoader())));
                instance.lastName = ((String) in.readValue((String.class.getClassLoader())));
                instance.email = ((String) in.readValue((String.class.getClassLoader())));
                instance.username = ((String) in.readValue((String.class.getClassLoader())));
                instance.userRole = ((String) in.readValue((String.class.getClassLoader())));
                instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
                instance.profilePicture=((String) in.readValue((String.class.getClassLoader())));
                instance.status = ((String) in.readValue((String.class.getClassLoader())));
                return instance;
            }

            public Content[] newArray(int size) {
                return (new Content[size]);
            }

        };

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
        public String getProfilePicture() {
            return profilePicture;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(firstName);
            dest.writeValue(lastName);
            dest.writeValue(email);
            dest.writeValue(username);
            dest.writeValue(userRole);
            dest.writeValue(id);
            dest.writeValue(status);
            dest.writeValue(profilePicture);
        }

        public int describeContents() {
            return 0;
        }

    }

}