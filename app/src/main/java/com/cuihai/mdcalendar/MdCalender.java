package com.cuihai.mdcalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * author:  崔海
 * time:    2017/5/17 23:23
 * name:
 * overview:
 * usage:
 */

public class MdCalender extends LinearLayout {
    private TextView tvPrev;
    private TextView tvNext;
    private TextView tvDate;
    private GridView gridView;
    private Calendar curDate = Calendar.getInstance();

    public MdCalender(Context context) {
        super(context);
        init(context);
    }

    public MdCalender(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MdCalender(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bindView(context);
        bindEvent();
        renderCalender();

    }

    private void renderCalender() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        tvDate.setText(sdf.format(curDate.getTime()));
        ArrayList<Date> list = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int prevDays = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);
        int maxCellCounts = 6 * 7;
        while (list.size() < maxCellCounts) {
            list.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        gridView.setAdapter(new CalendarAdapter(getContext(), list));
    }

    private void bindEvent() {
        tvPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH, -1);
                renderCalender();
            }
        });
        tvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curDate.add(Calendar.MONTH, 1);
                renderCalender();
            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        LayoutInflater layoutInflater;

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> list) {
            super(context, R.layout.item_calendar,list);
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Date date = getItem(position);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_calendar, parent, false);
            }
            int day = date.getDate();
            ((TextView) convertView).setText(String.valueOf(day));
            return convertView;
        }
    }

    private void bindView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.calendar, this);
        tvPrev = (TextView) findViewById(R.id.tvPrev);
        tvNext = (TextView) findViewById(R.id.tvNext);
        tvDate = (TextView) findViewById(R.id.tvDate);
        gridView = (GridView) findViewById(R.id.calendar_grid);
    }
}
