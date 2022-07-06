package com.texasimaginology.ticms.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.texasimaginology.ticms.Dashboard.RoutineDTO;
import com.texasimaginology.ticms.Dashboard.TeacherRoutineDTO;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.TableRowWithIndex;

import java.util.List;
import java.util.Random;

public class TableGenerator {
    private TableLayout tableLayout;
    private Context mContext;

    public TableGenerator(@NonNull Context mContext, @NonNull TableLayout tableLayout){
        this.tableLayout= tableLayout;
        this.mContext= mContext;
    }

    public void createTableHeader(String[] tableHeaders){
        TableRow trHeader = new TableRow(mContext);
        trHeader.setBackgroundColor(Color.GRAY);
        trHeader.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        for(String tableHeader: tableHeaders){
            TextView tvHeader = new TextView(mContext);
            tvHeader.setText(tableHeader);
            tvHeader.setTextColor(Color.WHITE);
            tvHeader.setGravity(Gravity.CENTER_HORIZONTAL);
            tvHeader.setTextSize(20f);
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Black.ttf");
            tvHeader.setTypeface(typeface, Typeface.BOLD);
            tvHeader.setPadding(32, 32, 16, 32);
            tvHeader.setLayoutParams( new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            trHeader.addView(tvHeader);// add the column to the table row here
        }
        tableLayout.addView(trHeader);
    }

    public void createTableDataRows(List<RoutineDTO> routineList){
        for(RoutineDTO routine: routineList){
            TableRowWithIndex trData = new TableRowWithIndex(mContext);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                trData.setBackground(mContext.getResources().getDrawable(R.drawable.table_row_background));
            }else
                trData.setBackgroundColor(Color.LTGRAY);

            trData.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            trData.addView(getView(routine.getDay()));
            trData.addView(getView(routine.getStartTime()+"-"+routine.getEndTime()));
            trData.addView(getView(routine.getSubject()));

            tableLayout.addView(trData,new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,                    //part4
                    TableLayout.LayoutParams.MATCH_PARENT));

            trData.setRowIndex(tableLayout.indexOfChild(trData));

//            if(trData.getRowIndex()%2==0)
//                trData.setBackgroundColor(Color.WHITE);

            trData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private View getView(String text) {
        TextView textView =new TextView(mContext);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16f);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
        textView.setTypeface(typeface, Typeface.NORMAL);
        textView.setPadding(32, 8, 8, 16);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams( new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        return textView;
    }

}
