package com.texasimaginology.ticms.Notification.PushNotification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationDto {

    @SerializedName("totalUnseenNotifications")
    @Expose
    private Integer totalUnseenNotifications;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    public Integer getTotalUnseenNotifications() {
        return totalUnseenNotifications;
    }

    public void setTotalUnseenNotifications(Integer totalUnseenNotifications) {
        this.totalUnseenNotifications = totalUnseenNotifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public class Notification {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("sentDate")
        @Expose
        private String sentDate;
        @SerializedName("seen")
        @Expose
        private Boolean seen;
        @SerializedName("sender")
        @Expose
        private Sender sender;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSentDate() {
            return sentDate;
        }

        public void setSentDate(String sentDate) {
            this.sentDate = sentDate;
        }

        public Boolean getSeen() {
            return seen;
        }

        public void setSeen(Boolean seen) {
            this.seen = seen;
        }

        public Sender getSender() {
            return sender;
        }

        public void setSender(Sender sender) {
            this.sender = sender;
        }


        public class Sender {

            @SerializedName("senderId")
            @Expose
            private Integer senderId;
            @SerializedName("senderName")
            @Expose
            private String senderName;
            @SerializedName("userRole")
            @Expose
            private String userRole;
            @SerializedName("profilePicture")
            @Expose
            private String profilePicture;

            public Integer getSenderId() {
                return senderId;
            }

            public void setSenderId(Integer senderId) {
                this.senderId = senderId;
            }

            public String getSenderName() {
                return senderName;
            }

            public void setSenderName(String senderName) {
                this.senderName = senderName;
            }

            public String getUserRole() {
                return userRole;
            }

            public void setUserRole(String userRole) {
                this.userRole = userRole;
            }

            public String getProfilePicture() {
                return profilePicture;
            }

            public void setProfilePicture(String profilePicture) {
                this.profilePicture = profilePicture;
            }

        }


    }


}






//package com.texasimaginology.ticms.Notification.PushNotification;
//
///**
// * Created by deepbhai on 11/30/17.
// */
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class NotificationDto {
//
//    @SerializedName("semester")
//    @Expose
//    private String semester;
//    @SerializedName("message")
//    @Expose
//    private String message;
//    @SerializedName("type")
//    @Expose
//    private String type;
//    @SerializedName("title")
//    @Expose
//    private String title;
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("sentDate")
//    @Expose
//    private String sentDate;
//    @SerializedName("seen")
//    @Expose
//    private Boolean seen;
//
//    public void setSentDate(String sentDate) {
//        this.sentDate = sentDate;
//    }
//
//    public void setSeen(Boolean seen) {
//        this.seen = seen;
//    }
//
//    public String getSentDate() {
//        return sentDate;
//    }
//
//    public Boolean getSeen() {
//        return seen;
//    }
//
//    public String getSemester() {
//        return semester;
//    }
//
//    public void setSemester(String semester) {
//        this.semester = semester;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//}