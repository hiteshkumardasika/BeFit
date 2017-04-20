package com.example.root.befit.calender;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.root.befit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Shakthi on 4/19/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private int hour, minute, am_pm, month, day;
    private Calendar c;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        am_pm = c.get(Calendar.AM_PM);

        //c.add(Calendar.HOUR_OF_DAY, 9);
        //c.add(Calendar.MINUTE, 1);
        System.out.println("hour: "+ (hour+am_pm)+"date "+ c.getTime());

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        boolean nextDay = false;

        if(hourOfDay < hour){
            c.add(Calendar.DATE, 1);
            c.add(Calendar.HOUR_OF_DAY, hourOfDay-hour);
            c.add(Calendar.MINUTE, minute-this.minute);
            System.out.println("end Time "+c.getTime());
            nextDay = true;
        }else if(hourOfDay == hour){
            if(minute <= this.minute){
                c.add(Calendar.DATE, 1);
                c.add(Calendar.MINUTE, minute - this.minute);
                nextDay = true;
            }else{
                c.add(Calendar.MINUTE, minute - this.minute);
            }
        }else{
            c.add(Calendar.HOUR_OF_DAY, hourOfDay-hour);
            c.add(Calendar.MINUTE, minute-this.minute);
        }

        System.out.println(c.getTime());
        long endTime = c.getTimeInMillis();

        TextView tv = (TextView) getActivity().findViewById(R.id.ScheduledTime);
        //Set a message for user
        tv.setText("Your chosen time is...\n\n");
        //Display the user changed time on TextView
        String amOrPm = "AM";
        if(hourOfDay >= 12){
            amOrPm = "PM";
        }
        if(hourOfDay > 12){
            hourOfDay = hourOfDay % 12;
        }
        String str = "";
        if(hourOfDay > 9) {
            str = String.valueOf(hourOfDay) + ":";
        }else{
            str = "0"+String.valueOf(hourOfDay) + ":";
        }

        if(minute < 10) {
            str += "0"+String.valueOf(minute)+" ";
        }else{
            str += String.valueOf(minute) + " ";
        }

        if(nextDay) {
            tv.setText("Scheduled Time: " + str + amOrPm +"(T)");
            str = "Scheduled Time: " + str + amOrPm +"(T)";
        }else{
            tv.setText("Scheduled Time: " + str + amOrPm +"");
            str = "Scheduled Time: " + str + amOrPm +"";
        }

        Intent i = new Intent();
        i.putExtra("selectedDate",endTime);
        i.putExtra("Time",str);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }
}
