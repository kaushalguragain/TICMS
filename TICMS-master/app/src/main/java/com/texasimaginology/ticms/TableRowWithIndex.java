package com.texasimaginology.ticms;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

public class TableRowWithIndex extends TableRow {
    private int rowIndex;
    public TableRowWithIndex(Context context) {
        super(context);
    }

    public TableRowWithIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
}
