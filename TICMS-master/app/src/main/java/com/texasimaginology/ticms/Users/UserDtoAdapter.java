package com.texasimaginology.ticms.Users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.texasimaginology.ticms.CheckError;
import com.texasimaginology.ticms.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by deepbhai on 9/15/17.
 */

public class UserDtoAdapter extends RecyclerView.Adapter<UserDtoAdapter.UserListHolder> {

    private LayoutInflater inflator;
    private Context context;
    private List<UserListDto.Content> userList = Collections.emptyList();
    private int rowLayout;
    private ClickListener clickListener;

    public UserDtoAdapter(List<UserListDto.Content> userList, int rowLayout, Context context) {
        this.context = context;
        this.userList = userList;
        this.rowLayout = rowLayout;
    }

    @Override
    public UserListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new UserListHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListHolder holder, int position) {
        holder.fullName.setText(CheckError.checkNullString(userList.get(position).getFirstName()) + " " + CheckError.checkNullString(userList.get(position).getLastName()));
        holder.email.setText(CheckError.checkNullString(userList.get(position).getEmail()));
        holder.phone.setText(CheckError.checkNullString(userList.get(position).getMobileNumber()));

        String picUrl=userList.get(position).getProfilePicture();

        if(picUrl!=null){
            Picasso.with(context)
                    .load(picUrl)
                    .into(holder.show);
        }

        //holder.show.setImageResource(R.drawable.user);
        //Decode Base64 to Bitmap and display in circle view
        /*String stringPicture=userList.get(position).getProfilePicture();
        if(stringPicture!=null) {
            byte[] picCode = Base64.decode(stringPicture, Base64.DEFAULT);
            Bitmap pic = BitmapFactory.decodeByteArray(picCode, 0, picCode.length);
            holder.show.setImageBitmap(pic);
        }*/
//        if(stringPicture==null){
//            holder.show.setBackgroundResource(R.drawable.user);
//        }

    }

    public void setUserClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFilter(List<UserListDto.Content> newList){
        userList = new ArrayList<>();
        userList.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class UserListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fullName, email, phone;
        ImageView show;
        private UserListDto.Content student;

        public UserListHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            fullName = v.findViewById(R.id.fullName);
            email = v.findViewById(R.id.email);
            phone = v.findViewById(R.id.phone);
            show = v.findViewById(R.id.list_image_view);
        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.itemClicked(v, userList.get(getPosition()), getPosition());
            }
        }

    }


    public interface ClickListener {

        void itemClicked(View v, UserListDto.Content userDTO, int position);
    }

}
