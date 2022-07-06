package com.texasimaginology.ticms.Users;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListDto implements Parcelable {

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;
    public final static Parcelable.Creator<UserListDto> CREATOR = new Creator<UserListDto>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserListDto createFromParcel(Parcel in) {
            return new UserListDto(in);
        }

        public UserListDto[] newArray(int size) {
            return (new UserListDto[size]);
        }

    }
            ;

    protected UserListDto(Parcel in) {
        this.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.contents, (UserListDto.Content.class.getClassLoader()));
    }

    public UserListDto() {
    }

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

    public class Content implements Parcelable
    {

        @SerializedName("customerId")
        @Expose
        private long customerId;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("middleName")
        @Expose
        private String middleName;
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
        @SerializedName("mobileNumber")
        @Expose
        private String mobileNumber;
        @SerializedName("profilePicture")
        @Expose
        private String profilePicture;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        public final Parcelable.Creator<Content> CREATOR = new Creator<Content>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Content createFromParcel(Parcel in) {
                return new Content(in);
            }

            public Content[] newArray(int size) {
                return (new Content[size]);
            }

        }
                ;

        protected Content(Parcel in) {
            this.firstName = ((String) in.readValue((String.class.getClassLoader())));
            this.middleName = ((String) in.readValue((String.class.getClassLoader())));
            this.lastName = ((String) in.readValue((String.class.getClassLoader())));
            this.email = ((String) in.readValue((String.class.getClassLoader())));
            this.username = ((String) in.readValue((String.class.getClassLoader())));
            this.userRole = ((String) in.readValue((String.class.getClassLoader())));
            this.mobileNumber = ((String) in.readValue((String.class.getClassLoader())));
            this.profilePicture = ((String) in.readValue((String.class.getClassLoader())));
            this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.status = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Content() {
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
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

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

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

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(firstName);
            dest.writeValue(middleName);
            dest.writeValue(lastName);
            dest.writeValue(email);
            dest.writeValue(username);
            dest.writeValue(userRole);
            dest.writeValue(mobileNumber);
            dest.writeValue(profilePicture);
            dest.writeValue(id);
            dest.writeValue(status);
        }

        public int describeContents() {
            return 0;
        }

    }

}