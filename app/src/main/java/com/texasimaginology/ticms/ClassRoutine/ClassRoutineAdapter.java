package com.texasimaginology.ticms.ClassRoutine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.texasimaginology.ticms.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by deepbhai on 2/15/18.
 */

public class ClassRoutineAdapter extends TableDataAdapter<RoutineDto>{
    private static final int TEXT_SIZE = 13;
    private  Context context;
    private int rowLayout;
    private List<RoutineResponseDto> routineResponseDtos = Collections.emptyList();

    public ClassRoutineAdapter(Context context, List<RoutineDto> data) {
        super(context, data);
//        this.context = context;
//        this.routineResponseDtos = routineResponseDtos;
//        this.rowLayout = rowLayout;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        RoutineDto classRoutineDto = getRowData(rowIndex);
        View renderedView = null;
        switch (columnIndex) {
            case 0:
                renderedView = getTime(classRoutineDto);
                break;
            case 1:
                renderedView = getSundaySubject(classRoutineDto);
                break;
            case 2:
                renderedView = getMondaySubject(classRoutineDto);
                break;
            case 3:
                renderedView = getTuesdaySubject(classRoutineDto);
                break;
            case 4:
                renderedView = getWednesdaySubject(classRoutineDto);
                break;
            case 5:
                renderedView = getThursdaySubject(classRoutineDto);
                break;
            case 6:
                renderedView = getFridaySubject(classRoutineDto);
                break;
            case 7:
                renderedView = getSaturdaySubject(classRoutineDto);
                break;
        }

        if (rowIndex % 2 != 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.color_grey));
        }

        return renderedView;

    }

    private View getSundaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getSunday());
    }

    private View getMondaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getMonday());
    }

    private View getTuesdaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getTuesday());
    }

    private View getWednesdaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getWednesday());
    }

    private View getThursdaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getThursday());
    }

    private View getFridaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getFriday());
    }

    private View getSaturdaySubject(RoutineDto classRoutineDto) {
        return renderString(classRoutineDto.getSaturday());
    }

    private View getTime(RoutineDto classRoutineDto) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//            return renderString(dateFormat.format(format.parse(classRoutineDto.getStartTime())));
        return renderString("N/A");
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(12, 10, 1, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }


}
