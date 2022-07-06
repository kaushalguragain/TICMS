package com.texasimaginology.ticms.Students;

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

public class StudentDtoAdapter extends RecyclerView.Adapter<StudentDtoAdapter.StudentListHolder> {

    private LayoutInflater inflator;
    private Context context;
    private List<StudentListDto.Contents> studentList = Collections.emptyList();
    private int rowLayout;
    private ClickListener clickListener;

    public StudentDtoAdapter(List<StudentListDto.Contents> userList, int rowLayout, Context context) {
        this.context = context;
        this.studentList = userList;
        this.rowLayout = rowLayout;
    }

    @Override
    public StudentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new StudentListHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentListHolder holder, int position) {
        holder.fullName.setText(CheckError.checkNullString(studentList.get(position).getFirstName()) +" " + CheckError.checkNullString(studentList.get(position).getLastName()));
        holder.email.setText(CheckError.checkNullString(studentList.get(position).getEmail()));
        holder.phone.setText(CheckError.checkNullString(studentList.get(position).getPhoneNumber()));
        holder.fullName.setText(CheckError.checkNullString(studentList.get(position).getFirstName()));
        String picUrl=studentList.get(position).getProfilePicture();

        if(picUrl!=null){
            Picasso.with(context)
                    .load(picUrl)
                    .into(holder.show);
        }
        //Decode Base64 to Bitmap and display in circle view
        /*String stringPicture=studentList.get(position).getProfilePicture();
        if(stringPicture!=null) {
            byte[] picCode = Base64.decode(stringPicture, Base64.DEFAULT);
            Bitmap pic = BitmapFactory.decodeByteArray(picCode, 0, picCode.length);
            holder.show.setImageBitmap(pic);
        }*/
    }

    public void setStudentClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFilter(List<StudentListDto.Contents> newList){
        studentList = new ArrayList<>();
        studentList.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }


    public class StudentListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView fullName, email, phone;
        ImageView show;

        private StudentListDto.Contents student;

        public StudentListHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            fullName = v.findViewById(R.id.fullName);
            email = v.findViewById(R.id.email);
            phone = v.findViewById(R.id.phone);
            show=v.findViewById(R.id.list_image_view);
        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.itemClicked(v, studentList.get(getPosition()), getPosition());
            }
        }

    }


    public interface ClickListener {

        void itemClicked(View v, StudentListDto.Contents userDTO, int position);
    }

}
