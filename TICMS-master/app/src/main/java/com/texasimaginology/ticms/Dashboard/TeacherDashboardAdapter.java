package com.texasimaginology.ticms.Dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TextView;

import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.util.RecyclerviewItemAnimator;
import com.texasimaginology.ticms.util.TableGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.codecrafters.tableview.TableHeaderAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TeacherDashboardAdapter extends RecyclerView.Adapter<TeacherDashboardAdapter.TeacherViewHolder> {
    private Context mContext;
    private List<TeacherRoutineDTO> teacherRoutineDTOList=new ArrayList<>();
    private LayoutInflater inflater;
    private static final String[] TABLE_HEADERS = { "Day","Time","Subject"};
    final int COLUMN_WIDTH = 100;
    private int lastPosition = -1;


    public TeacherDashboardAdapter(Context mContext, List<TeacherRoutineDTO> teacherRoutineDTOList){
        this.mContext=mContext;
        this.teacherRoutineDTOList=teacherRoutineDTOList;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_routine_row, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {

        holder.tvCourse.setText(teacherRoutineDTOList.get(position).getCourse());
        holder.tvSemester.setText(teacherRoutineDTOList.get(position).getSemester());

        //For Routine render
//        TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(mContext, 3, 100);
//        TableColumnWeightModel columnWeightModel= new TableColumnWeightModel(3);
//        holder.tvRoutine.setColumnModel(columnWeightModel);
//        holder.tvRoutine.setHeaderAdapter(new SimpleTableHeaderAdapter(mContext, TABLE_HEADERS));
//
//        TeacherTableViewAdapter teacherTableViewAdapter = new TeacherTableViewAdapter(mContext,3,teacherRoutineDTOList.get(position).getRoutinesList());
//        holder.tvRoutine.setDataAdapter(teacherTableViewAdapter);

        TableGenerator tableGenerator= new TableGenerator(mContext,holder.tableLayout);
        tableGenerator.createTableHeader(TABLE_HEADERS);
//        List<RoutineDTO> routineDTOList=new ArrayList<>();
//        for(int i=0; i<7;i++){
//            RoutineDTO routineDTO= new RoutineDTO();
//            routineDTO.setDay("Sunday");
//            routineDTO.setStartTime("7:15");
//            routineDTO.setEndTime("8:15");
//            routineDTO.setSubject("TOC");
//            routineDTOList.add(routineDTO);
//
//        }

        tableGenerator.createTableDataRows(teacherRoutineDTOList.get(position).getRoutinesList());

        lastPosition= RecyclerviewItemAnimator.setAnimation(mContext,holder.itemView, position, lastPosition);

    }

    @Override
    public int getItemCount() {
        return teacherRoutineDTOList.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView tvCourse;
        private TextView tvSemester;
//        private TableView<RoutineDTO> tvRoutine;
        private TableLayout tableLayout;

        public TeacherViewHolder(View itemView) {
            super(itemView);

            cardView= itemView.findViewById(R.id.cv_routine_holder);
            tvCourse= itemView.findViewById(R.id.tv_routine_course);
            tvSemester= itemView.findViewById(R.id.tv_routine_semester);
//            tvRoutine= itemView.findViewById(R.id.tv_routine_table);
            tableLayout= itemView.findViewById(R.id.tv_routine_table_custom);
        }
    }


}
