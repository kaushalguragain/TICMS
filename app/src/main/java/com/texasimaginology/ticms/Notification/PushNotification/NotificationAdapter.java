package com.texasimaginology.ticms.Notification.PushNotification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.util.RecyclerviewItemAnimator;

import java.util.Collections;
import java.util.List;


/**
 * Created by deepbhai on 11/30/17.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationListHolder>{
    private Context context;
    private List<NotificationDto.Notification> notificationDtoList = Collections.emptyList();
    private int rowLayout;
    private String title,message,semester;
    private ClickListener clickListener;
    private int lastPosition= -1;
    public NotificationAdapter(List<NotificationDto.Notification> notificationDtoList, int rowLayout, Context context) {
        this.context = context;
        this.notificationDtoList = notificationDtoList;
        this.rowLayout = rowLayout;
    }

    @Override
    public NotificationAdapter.NotificationListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NotificationAdapter.NotificationListHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationListHolder holder, int position) {

        holder.notificationTitle.setText(notificationDtoList.get(position).getTitle());
        holder.notificationMessage.setText(notificationDtoList.get(position).getMessage());
        holder.notificationSender.setText(notificationDtoList.get(position).getSender().getSenderName());
        String picUrl=notificationDtoList.get(position).getSender().getProfilePicture();

        if(picUrl!=null && !picUrl.isEmpty()){
            Picasso.with(context)
                    .load(picUrl)
                    .into(holder.senderPic);
        }
        Long time= System.currentTimeMillis();
        Long sentTime=Long.parseLong(String.valueOf(notificationDtoList.get(position).getSentDate()));
        Long diffTime=time-sentTime;
        Long diffTimeInSec=diffTime/1000;
        if(diffTimeInSec<30){
            holder.notificationTime.setText("Just Now");
        }
        else if(diffTimeInSec>=30 && diffTimeInSec<60){
            holder.notificationTime.setText("Few seconds ago");
        }
        else if(diffTimeInSec>=60 && diffTimeInSec<3600){
            holder.notificationTime.setText(diffTimeInSec/60+" "+"Minute ago");
        }
        else if(diffTimeInSec>=3600 && diffTimeInSec<86400){
            holder.notificationTime.setText(diffTimeInSec/3600+" "+"Hours ago");
        }
        else if(diffTimeInSec>=86400 && diffTimeInSec<2628002.88){
            holder.notificationTime.setText(diffTimeInSec/86400+" "+"Day ago");
        }
        else if(diffTimeInSec>=2628002.88 && diffTimeInSec<31536000){
            holder.notificationTime.setText(diffTimeInSec/2628002.88 +" "+"Month ago");
        }
        else{
            holder.notificationTime.setText("One or Many years Ago");

        }
        title=notificationDtoList.get(position).getTitle();
        message=notificationDtoList.get(position).getMessage();

       lastPosition= RecyclerviewItemAnimator.setAnimation(context, holder.itemView, position, lastPosition);

    }

    public void setNotificationClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return notificationDtoList.size();
    }


    public class NotificationListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView notificationTitle;
        TextView notificationMessage;
        TextView notificationTime;
        TextView notificationSender;
        ImageView senderPic;
        public NotificationListHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            notificationTitle = v.findViewById(R.id.notification_title);
            notificationMessage = v.findViewById(R.id.notification_body);
            notificationTime=v.findViewById(R.id.notification_time);
            notificationSender=v.findViewById(R.id.notification_sender);
            senderPic=v.findViewById(R.id.notification_sender_image);
        }

        @Override
        public void onClick(View view) {
            if(clickListener!=null){
                clickListener.itemClicked(view, notificationDtoList.get(getPosition()), getPosition());
            }
        }
    }
    public interface ClickListener {

        void itemClicked(View v, NotificationDto.Notification notificationDto, int position);
    }

}
