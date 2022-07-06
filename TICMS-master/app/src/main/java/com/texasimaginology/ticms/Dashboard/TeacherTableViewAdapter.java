package com.texasimaginology.ticms.Dashboard;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.model.TableColumnModel;

public class TeacherTableViewAdapter extends TableDataAdapter<RoutineDTO> {
   List<RoutineDTO> teacherRoutines;
    Context mContext;
    public TeacherTableViewAdapter(Context context, int columnCount, List<RoutineDTO> data) {
        super(context,columnCount, data);
        teacherRoutines=data;
        mContext=context;

    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        RoutineDTO teacherRoutineDto = getRowData(rowIndex);
        View tableRoutineView= null;
        Log.d("Inside getCellView::", teacherRoutineDto.getDay());
        switch (columnIndex){
            case 0:
                tableRoutineView=makeView(teacherRoutineDto.getDay());
                Log.d("Day Value::", teacherRoutineDto.getDay());
                break;
            case 1:
                tableRoutineView=makeView(teacherRoutineDto.getStartTime()+"-"+teacherRoutineDto.getEndTime());
                break;
            case 2:
                tableRoutineView=makeView(teacherRoutineDto.getSubject());
                Log.d("Subject Value::", teacherRoutineDto.getSubject());
                break;
        }
        return tableRoutineView;
    }


    private View makeView(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(13);
        return textView;
    }
}


