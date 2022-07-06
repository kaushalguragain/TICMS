package com.texasimaginology.ticms.Subjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.texasimaginology.ticms.CheckError;
import com.texasimaginology.ticms.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by deepbhai on 2/10/18.
 */

public class SubjectsDtoAdapter extends RecyclerView.Adapter<SubjectsDtoAdapter.SubjectsDtoHolder>  {

    private LayoutInflater inflator;
    private Context context;
    private List<SubjectsDto> subjectsDtos = Collections.emptyList();
    private int rowLayout;

    public SubjectsDtoAdapter(List<SubjectsDto> subjectsDtos, int rowLayout, Context context) {
        this.context = context;
        this.subjectsDtos = subjectsDtos;
        this.rowLayout = rowLayout;
    }

    @Override
    public SubjectsDtoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SubjectsDtoHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectsDtoHolder holder, int position) {
        holder.subjectName.setText(CheckError.checkNullString(subjectsDtos.get(position).getSubjectName()));
        holder.subjectDescription.setText(CheckError.checkNullString(subjectsDtos.get(position).getSubDescription()));
        holder.subjectCode.setText(CheckError.checkNullString(subjectsDtos.get(position).getSubjectCode()));
        holder.creditHour.setText("Credit Hour:  " + CheckError.checkNullString(String.valueOf(subjectsDtos.get(position).getCreditHour())));
    }

    @Override
    public int getItemCount() {
        return subjectsDtos.size();
    }

    public void setFilter(List<SubjectsDto> newList){
        subjectsDtos = new ArrayList<>();
        subjectsDtos.addAll(newList);
        notifyDataSetChanged();
    }

    public class SubjectsDtoHolder extends RecyclerView.ViewHolder{
        TextView subjectName, subjectDescription, subjectCode, creditHour;
        public SubjectsDtoHolder(View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            subjectDescription = itemView.findViewById(R.id.subject_description);
            subjectCode = itemView.findViewById(R.id.subject_code);
            creditHour = itemView.findViewById(R.id.credit_hour);
        }
    }
}
