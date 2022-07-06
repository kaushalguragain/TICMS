package com.texasimaginology.ticms.Login;

/**
 * Created by deepbhai on 9/13/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDto{

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user")
    private User user;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    private String client;

    public LoginDto(String password, String username, String deviceId, String client) {
        this.password = password;
        this.username = username;
        this.deviceId = deviceId;
        this.client=client;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class User {
        @SerializedName("loginId")
        @Expose
        private Long loginId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("customerId")
        @Expose
        private Long customerId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("middleName")
        @Expose
        private String middleName;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("userRole")
        @Expose
        private String userRole;
        @SerializedName("loginType")
        @Expose
        private String loginType;
        @SerializedName("profilePicture")
        @Expose
        private String profilePicture;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;

        public Long getLoginId() {
            return loginId;
        }

        public void setLoginId(Long loginId) {
            this.loginId = loginId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public Long getCustomerId() {
           // Long csId= Long.valueOf(100);
           // if(customerId!=null){
            return customerId;
          //  }
          //  return csId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

    }

}


