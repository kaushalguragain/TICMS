package com.texasimaginology.ticms.Counseling;


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
 * Created by yubar on 5/25/2018.
 */

public class CounselingDtoAdapter extends RecyclerView.Adapter<CounselingDtoAdapter.CounsellingListHolder> {
    private LayoutInflater inflator;
    private Context context;
    private List<CounselingListDto> counselingList = Collections.emptyList();
    private int rowLayout;
    private ClickListener clickListener;


    public CounselingDtoAdapter(List<CounselingListDto> counselingList, int rowLayout, Context context) {
        this.context = context;
        this.counselingList = counselingList;
        this.rowLayout = rowLayout;
    }

    @Override
    public CounsellingListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CounsellingListHolder(view);
    }

    @Override
    public void onBindViewHolder(CounsellingListHolder holder, int position) {
        holder.fullName.setText(CheckError.checkNullString(counselingList.get(position).getFullName()));
        holder.email.setText(CheckError.checkNullString(counselingList.get(position).getEmail()));
        holder.course.setText(CheckError.checkNullString(counselingList.get(position).getCourseName()));
        String picUrl=counselingList.get(position).getProfilePicture();

        if(picUrl!=null){
            Picasso.with(context)
                    .load(picUrl)
                    .into(holder.show);
        }

        //Decode Base64 to Bitmap and display in circle view
        /*String stringPicture=counselingList.get(position).getProfilePicture();
        if(stringPicture!=null) {
            byte[] picCode = Base64.decode(stringPicture, Base64.DEFAULT);
            Bitmap pic = BitmapFactory.decodeByteArray(picCode, 0, picCode.length);
            holder.show.setImageBitmap(pic);
        }*/
    }

    public void setCounselingClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFilter(List<CounselingListDto> newList){
        counselingList = new ArrayList<>();
        counselingList.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return counselingList.size();
    }


    public class CounsellingListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView fullName, email, course;
        ImageView show;
        private CounselingListDto Counselling;

        public CounsellingListHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            fullName = v.findViewById(R.id.fullName);
            email = v.findViewById(R.id.email);
            course = v.findViewById(R.id.phone);
            show=v.findViewById(R.id.list_image_view);
        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null){
                clickListener.itemClicked(v, counselingList.get(getPosition()), getPosition());
            }
        }

    }


    public interface ClickListener {

        void itemClicked(View v, CounselingListDto teacherDTO, int position);
    }

}
