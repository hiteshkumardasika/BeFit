package com.example.root.befit.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.befit.R;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by root on 4/15/17.
 */

public class FragmentTileFour extends Fragment {

    DecoView decoView;
    SeriesItem background, countDownArc;
    int countDownIndex;
    int percentage;
    private Handler handler;
    Timer timer;

    public FragmentTileFour() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tile_four, container, false);
        decoView = (DecoView) view.findViewById(R.id.dynamicArcViewCalender);
        decoView.deleteAll();
        decoView.configureAngles(180, 0);
        background = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .build();

        countDownArc = new SeriesItem.Builder(Color.parseColor("#20A112"))
                .setRange(0,100,100)
                .setInitialVisibility(true)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        decoView.addSeries(background);
        countDownIndex = decoView.addSeries(countDownArc);

        handler = new Handler();
        timer = new Timer();
        timer.schedule(new CountDownTimerTask(), (long)0, (long)1000);


        percentage = 100;


        return view;
    }

    class CountDownTimerTask extends TimerTask{

        @Override
        public void run() {
            handler.post(
                    new Runnable() {
                        @Override
                        public void run() {

                            if(percentage < 20){
                                countDownArc = new SeriesItem.Builder(Color.parseColor("#D70A0A"))
                                        .setRange(0,100,100)
                                        .setInitialVisibility(true)
                                        .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                                        .build();
                            }else if(percentage < 50){
                                countDownArc = new SeriesItem.Builder(Color.parseColor("#C2D70A"))
                                        .setRange(0,100,100)
                                        .setInitialVisibility(true)
                                        .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                                        .build();
                            }

                            decoView.addEvent(new DecoEvent.Builder(--percentage).setIndex(countDownIndex).setDelay(0).build());
                        }
                    }
            );
        }
    }


}