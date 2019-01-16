package com.pengshan.latte_core.date;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DateDIalogUtil {

    public interface IDateListener{
        void onDateChange(String date);
    }


    private IDateListener mDateListener;

    public void setDateListener(IDateListener listener){
        this.mDateListener=listener;
    }

    public void showDialog(Context context){
        final LinearLayout ll=new LinearLayout(context);
        final DatePicker picker=new DatePicker(context);
        final LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        picker.setLayoutParams(lp);

        picker.init(1990, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                final Calendar calendar=Calendar.getInstance();
                calendar.set(year,monthOfYear,dayOfMonth);
                final SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());
                final String date=format.format(calendar.getTime());
                if (mDateListener!=null){
                    mDateListener.onDateChange(date);
                }
            }
        });

        ll.addView(picker);

        new AlertDialog.Builder(context)
                .setTitle("选择日期")
                .setView(ll)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }
}











