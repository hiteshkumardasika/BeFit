package com.example.root.befit.fragments;

/**
 * Created by root on 3/30/17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.Date;

import android.content.Loader;
import android.app.LoaderManager;
import android.widget.RemoteViews;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import api.models.DailyActivitySummary;
import api.models.Goals;
import api.models.Summary;
import api.services.ActivityService;
import api.loaders.ResourceLoaderResult;


public class FragmentTileTwo extends Fragment implements LoaderManager.LoaderCallbacks<ResourceLoaderResult<DailyActivitySummary>>, View.OnClickListener {

    DecoView decoView;
    TextView textView;

    ImageView bikeImage, runImage, walkImage, calImage, sumImage;

    protected final String TAG = getClass().getSimpleName();
    private static final int FRAGMENT_LOADER_ID = 1;

    private static final int BIKE_INDEX = 0;
    private static final int RUN_INDEX = 1;
    private static final int WALK_INDEX = 2;
    private static final int CAL_INDEX = 3;

    DailyActivitySummary data;

    String format = "%.0f%%";

    final private int[] activityColors = {
            Color.parseColor("#FFFF8800"),
            Color.parseColor("#FFFF4444"),
            Color.parseColor("#DEE50B"),
            Color.parseColor("#389105"),
            Color.parseColor("#C3C4C2"),
    };

    SeriesItem bike, run, walk, cal;
    SeriesItem background, background2, background3, background4;

    @Override
    public void onResume() {
        super.onResume();
    }

    public FragmentTileTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tile_two, container, false);
        decoView = (DecoView) view.findViewById(R.id.dynamicArcView);
        textView = (TextView) view.findViewById(R.id.textPercentage);

        bikeImage = (ImageView) view.findViewById(R.id.bikeActivity1);
        bikeImage.setOnClickListener(this);

        sumImage = (ImageView) view.findViewById(R.id.sumActivity5);
        sumImage.setOnClickListener(this);

        runImage = (ImageView) view.findViewById(R.id.runActivity2);
        runImage.setOnClickListener(this);

        walkImage = (ImageView) view.findViewById(R.id.walkActivity3);
        walkImage.setOnClickListener(this);

        calImage = (ImageView) view.findViewById(R.id.calActivity4);
        calImage.setOnClickListener(this);

        getLoaderManager().initLoader(FRAGMENT_LOADER_ID, null, this).forceLoad();

        return view;

    }

    @Override
    public Loader<ResourceLoaderResult<DailyActivitySummary>> onCreateLoader(int i, Bundle bundle) {
        return ActivityService.getDailyActivitySummaryLoader(getActivity(), new Date());
    }

    @Override
    public void onLoadFinished(Loader<ResourceLoaderResult<DailyActivitySummary>> loader, ResourceLoaderResult<DailyActivitySummary> dailyActivitySummaryResourceLoaderResult) {
        switch (dailyActivitySummaryResourceLoaderResult.getResultType()) {
            case ERROR:
                Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                break;
            case EXCEPTION:
                Log.e(TAG, "Error loading data", dailyActivitySummaryResourceLoaderResult.getException());
                Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                break;
        }
        loadData(dailyActivitySummaryResourceLoaderResult.getResult());
    }

    public void createArcsForSum(){

        decoView.deleteAll();
        decoView.configureAngles(280, 0);

        //Create background series track
        background = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .build();
        background2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 100, 100)
                .setInset(new PointF(80f, 80f))
                .setInitialVisibility(true)
                .build();
        background3 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 100, 100)
                .setInset(new PointF(160f, 160f))
                .setInitialVisibility(true)
                .build();
        background4 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 100, 100)
                .setInset(new PointF(240f, 240f))
                .setInitialVisibility(true)
                .build();

        //create dataSeries or foreground track
        bike = new SeriesItem.Builder(activityColors[BIKE_INDEX])
                .setRange(0, 100, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();
        run = new SeriesItem.Builder(activityColors[RUN_INDEX])
                .setRange(0, 100, 0)
                .setInset(new PointF(80f, 80f))
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        walk = new SeriesItem.Builder(activityColors[WALK_INDEX])
                .setRange(0, 100, 0)
                .setInset(new PointF(160f, 160f))
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        cal = new SeriesItem.Builder(activityColors[CAL_INDEX])
                .setRange(0, 100, 0)
                .setInset(new PointF(240f, 240f))
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();
    }

    public void loadData(DailyActivitySummary data) {
        this.data = data;
        sumAction();
    }

    public void sumAction(){
        createArcsForSum();
        //Compute calories percentage
        final Summary summary = data.getSummary();
        final Goals goals = data.getGoals();
        double calBurnt = summary.getCaloriesOut();
        double calGoal = goals.getCaloriesOut();
        int caloriesPercentage = (int)((calBurnt/calGoal) * 100);

        decoView.addSeries(background);
        decoView.addSeries(background2);
        decoView.addSeries(background3);
        decoView.addSeries(background4);
        int bikeIndex = decoView.addSeries(bike);
        int runIndex = decoView.addSeries(run);
        int walkIndex = decoView.addSeries(walk);
        int calIndex = decoView.addSeries(cal);

        decoView.addEvent(new DecoEvent.Builder(caloriesPercentage).setIndex(bikeIndex).setDelay(1000).build());
        decoView.addEvent(new DecoEvent.Builder(50).setIndex(runIndex).setDelay(2000).build());
        decoView.addEvent(new DecoEvent.Builder(80).setIndex(walkIndex).setDelay(3000).build());
        decoView.addEvent(new DecoEvent.Builder(30).setIndex(calIndex).setDelay(4000).build());

        //Toast toast = Toast.makeText(this.getContext(), data.+" activites", Toast.LENGTH_LONG);
        //toast.show();

        bike.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (format.contains("%%")) {
                    float percentFilled = ((currentPosition - bike.getMinValue()) / (bike.getMaxValue() - bike.getMinValue()));
                    textView.setText(String.format(format, percentFilled * 100f));
                } else {
                    textView.setText(String.format(format, currentPosition));
                }
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
    }

    public void restAction(int activity) {
        final Summary summary = data.getSummary();
        final Goals goals = data.getGoals();
        Toast.makeText(getActivity(), "Calories burnt" + summary.getCaloriesOut() + " goal was" + goals.getCaloriesOut(), Toast.LENGTH_LONG).show();
        decoView.configureAngles(280, 0);
        decoView.deleteAll();

        SeriesItem seriesItem = new SeriesItem.Builder(activityColors[activity])
                .setRange(0, 100, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        decoView.addSeries(background);
        int bikeIndex = decoView.addSeries(seriesItem);

        int percent = 0;

        switch (activity){
            case BIKE_INDEX:
                percent = 70;
                break;
            case RUN_INDEX:
                percent = 30;
                break;
            case WALK_INDEX:
                percent =50;
                break;
            case CAL_INDEX:
                percent =60;
                break;
        }

        decoView.addEvent(new DecoEvent.Builder(percent).setIndex(bikeIndex).setDelay(1000).build());

        bike.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (format.contains("%%")) {
                    float percentFilled = ((currentPosition - bike.getMinValue()) / (bike.getMaxValue() - bike.getMinValue()));
                    textView.setText(String.format(format, percentFilled * 100f));
                } else {
                    textView.setText(String.format(format, currentPosition));
                }
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
    }

    public void createNotification(int burnedCalories, int targetCalories) {
        Intent intent = new Intent(getContext(), FragmentTileTwo.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bmpImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.fitbit);
        NotificationCompat.BigPictureStyle bpS = new NotificationCompat.BigPictureStyle().bigPicture(bmpImage);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                // Set Icon
                .setSmallIcon(R.drawable.fitbit)
                // Set Ticker Message
                .setTicker("Testing")
                // Dismiss Notification
                .setAutoCancel(true)
                .addAction(R.drawable.gym, "Next", pendingIntent)
                .setStyle(bpS);
        // Set PendingIntent into Notification
        //.setContentIntent(pendingIntent);
        RemoteViews notificationView = new RemoteViews(getContext().getPackageName(), R.layout.status_bar);
        RemoteViews bigNotificationView = new RemoteViews(getContext().getPackageName(), R.layout.status_bar_expanded);

        builder.setContent(getComplexNotificationView("CALORIES BURNT", String.valueOf(burnedCalories), notificationView));
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setCustomBigContentView(bigNotificationView);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private RemoteViews getComplexNotificationView(String contentTitle, String contentText, RemoteViews notificationView) {
        notificationView.setTextViewText(R.id.title, contentTitle);
        notificationView.setTextViewText(R.id.text, contentText);
        return notificationView;
    }

    @Override
    public void onLoaderReset(Loader<ResourceLoaderResult<DailyActivitySummary>> loader) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == bikeImage.getId()){
            restAction(BIKE_INDEX);
        }else if(v.getId() == sumImage.getId()){
            sumAction();
        }else if(v.getId() == runImage.getId()){
            restAction(RUN_INDEX);
        }else if(v.getId() == walkImage.getId()){
            restAction(WALK_INDEX);
        }else if(v.getId() == calImage.getId()){
            restAction(CAL_INDEX);
        }
    }
}