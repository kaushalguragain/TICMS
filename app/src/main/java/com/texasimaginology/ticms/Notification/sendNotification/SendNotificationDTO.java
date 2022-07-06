package com.texasimaginology.ticms.Notification.sendNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rojin on 12/22/2017.
 */

public class SendNotificationDTO{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;
//    @SerializedName("teamId")
//    @Expose
//    private Long teamId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("teamId")
    @Expose
    private List<Long>teamId;

        public SendNotificationDTO(String id,List<Long>teamIds, String title, String message,String type) {
            this.message = message;
            this.teamId = teamIds;
            this.title = title;
            this.id=id;
            this.type=type;
        }


        public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Long> getTeamId() {
        return teamId;
    }

    public void setTeamId(List<Long> teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
