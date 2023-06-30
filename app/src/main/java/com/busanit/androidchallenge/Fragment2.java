package com.busanit.androidchallenge;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    private ArrayList<Lesson> lessons;
    private GridLayout gridLayout;
    private int cellWidth, cellHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);
        gridLayout = rootView.findViewById(R.id.gridTable);

        ArrayList<Lesson> lessons = getLessons();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        cellWidth = screenWidth / 6;
        cellHeight = 200;

        drawTable(rootView);

        return rootView;
    }

    private ArrayList<Lesson> getLessons() {
        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        return lessons;
    }

    private void drawTable(ViewGroup rootView) {
        String[] days = {"월", "화", "수", "목", "금"};
        String[] times = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};

        // 첫 번째 행(요일)
        for (int col = 1; col < gridLayout.getColumnCount(); col++) {
            TextView day = new TextView(getContext());
            day.setText(days[col-1]);
            day.setGravity(Gravity.CENTER);
            day.setBackgroundResource(R.drawable.tablecell);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(col, GridLayout.FILL, 1f);
            params.rowSpec = GridLayout.spec(0, GridLayout.FILL, 1f);
            day.setLayoutParams(params);
            gridLayout.addView(day);
        }

        // 첫 번째 열(시간)
        for (int row = 1; row < gridLayout.getRowCount()-1; row++) {
            if(row == 9) continue;
            TextView time = new TextView(getContext());
            time.setText(times[row-1]);
            time.setGravity(Gravity.CENTER);
            time.setBackgroundResource(R.drawable.tablecell);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(0, GridLayout.FILL, 1f);
            params.rowSpec = GridLayout.spec(row, GridLayout.FILL, 1f);
            time.setLayoutParams(params);
            gridLayout.addView(time);
        }
    }
}