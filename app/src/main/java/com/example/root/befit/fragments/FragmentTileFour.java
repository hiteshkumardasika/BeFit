package com.example.root.befit.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.befit.R;
import com.example.root.befit.calender.TimePickerFragment;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by root on 4/15/17.
 */

public class FragmentTileFour extends Fragment implements View.OnClickListener {

    public static final int CALENDAR_FRAGMENT=1;

    DecoView decoView;
    SeriesItem background, countDownArc;
    int countDownIndex;
    long currentTime;

    private Handler handler;
    Timer timer;
    long endTime;
    long max = 86400000;
    float percent;
    Calendar c;
    String endTimeS="";

    Button changeTime;
    TextView progressText, calendarText;

    public FragmentTileFour() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY,6);

        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        String amOrPm = "AM";
        if(hourOfDay >= 12){
            amOrPm = "PM";
        }

        if(hourOfDay > 12){
            hourOfDay = hourOfDay % 12;
        }

        endTimeS = hourOfDay+":"+min+" "+amOrPm;

        endTime = c.getTimeInMillis();
        percent = (((float)(endTime - Calendar.getInstance().getTimeInMillis()))/max) * 100;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tile_four, container, false);

        changeTime = (Button) view.findViewById(R.id.ChangeTime);
        changeTime.setOnClickListener(this);

        progressText = (TextView) view.findViewById(R.id.textPercentage);
        calendarText = (TextView) view.findViewById(R.id.ScheduledTime);

        calendarText.setText("Scheduled Time: "+endTimeS);

        decoView = (DecoView) view.findViewById(R.id.dynamicArcViewCalender);
        decoView.deleteAll();
        decoView.configureAngles(180, 0);
        background = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .build();

        currentTime = SystemClock.currentThreadTimeMillis();

        countDownArc = new SeriesItem.Builder(Color.parseColor("#20A112"))
                .setRange(0,100,percent)
                .setInitialVisibility(true)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        decoView.addSeries(background);
        countDownIndex = decoView.addSeries(countDownArc);

        handler = new Handler();
        timer = new Timer();
        timer.schedule(new CountDownTimerTask(), (long)0, (long)1000);

        Date currentDate = new Date();
        currentDate.getTime();

        return view;
    }

    @Override
    public void onClick(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setTargetFragment(this,CALENDAR_FRAGMENT);
        newFragment.show(getFragmentManager(),"TimePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CALENDAR_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    // here the part where I get my selected date from the saved variable in the intent and the displaying it.
                    Bundle bundle = data.getExtras();
                    long resultDate = bundle.getLong("selectedDate", endTime);
                    System.out.println("Time out: "+resultDate);
                    endTime = resultDate;
                }
                break;
        }
    }

    class CountDownTimerTask extends TimerTask{

        @Override
        public void run() {
            handler.post(
                    new Runnable() {
                        @Override
                        public void run() {

                            Calendar c = Calendar.getInstance();
                            long current = c.getTimeInMillis();
                            long diff = endTime - current;
                            float percentage = (((float)diff)/max) *100;
                            //percentage = 100.0f - percentage;
                            //System.out.println("percentage "+ endTime +" "+current);
                            int seconds = (int) (diff / 1000) % 60 ;
                            int minutes = (int) ((diff / (1000*60)) % 60);
                            int hours   = (int) ((diff / (1000*60*60)) % 24);

                            if( 0.0f < percentage && percentage < 10.0f) {
                                progressText.setText(hours+" Hrs "+ minutes + " Mins "+ seconds+" Secs ");
                                decoView.addEvent(new DecoEvent.Builder(percentage).setIndex(countDownIndex).setDelay(0).setColor(Color.parseColor("#D70A0A")).build());
                                System.out.println("percentage 1 "+ percentage);
                            }else if(0.0f < percentage && percentage < 20.0f){
                                progressText.setText(hours+" Hrs "+ minutes + " Mins "+ seconds+" Secs ");
                                decoView.addEvent(new DecoEvent.Builder(percentage).setIndex(countDownIndex).setDelay(0).setColor(Color.parseColor("#C2D70A")).build());
                                System.out.println("percentage 2 "+ percentage);
                            }else if(0.0f < percentage){
                                progressText.setText(hours+" Hrs "+ minutes + " Mins "+ seconds+" Secs ");
                                decoView.addEvent(new DecoEvent.Builder(percentage).setIndex(countDownIndex).setDelay(0).build());
                                System.out.println("percentage 3 "+ percentage);
                            }
                        }
                    }
            );
        }
    }


}