package com.girish.todoister.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.girish.todoister.R;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Utils {
    public static String getFormattedDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }
    public static void  hideSoftSoftware(View view){
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static int priorityToId(@NonNull Priority priority){
        int id = R.id.radioButton_high;
        switch (priority){
            case HIGH:
                id= R.id.radioButton_high;
                break;
            case MEDIUM:
                id = R.id.radioButton_med;
                break;
            case LOW:
                id = R.id.radioButton_low;
                break;
            default:
                break;
        }
        return id;
    }
}
