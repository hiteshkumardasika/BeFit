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

import com.example.root.befit.R;
import com.example.root.befit.activites.BeFitActivites;
import com.google.gson.Gson;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Loader;
import android.app.LoaderManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import api.models.ActivityCaloriesIntra;
import api.models.ActivityDistanceIntra;
import api.models.ActivityStepsIntra;
import api.models.ActivityStepsIntraDay;
import api.models.DailyActivitySummary;
import api.models.DataSetIntraDay;
import api.models.Distance;
import api.models.Goals;
import api.models.Summary;
import api.services.ActivityCaloriesIntraDayService;
import api.services.ActivityDistanceIntraDayService;
import api.services.ActivityService;
import api.loaders.ResourceLoaderResult;
import api.services.ActivityStepsIntraDayService;


public class FragmentTileTwo extends Fragment implements View.OnClickListener {

    DecoView decoView;
    TextView textView, textViewGoals;
    ImageButton refreshButton;
    ImageView bikeImage, runImage, walkImage, calImage, sumImage;

    protected final String TAG = getClass().getSimpleName();
    private static final int DAILY_ACTIV__LOADER_ID = 1;
    private static final int INTRA_DAY_STEPS_LOADER_ID = 2;
    private static final int INTRA_DAY_DISTANCE_LOADER_ID = 3;
    private static final int INTRA_DAY_CALORIES_LOADER_ID = 4;


    private static final int BIKE_INDEX = 0;
    private static final int RUN_INDEX = 1;
    private static final int WALK_INDEX = 2;
    private static final int CAL_INDEX = 3;
    BeFitActivites goals;

    private boolean sumDisplayed;

    DailyActivitySummary data;
    ActivityStepsIntra stepsIntraData;
    ActivityDistanceIntra distanceIntra;
    ActivityCaloriesIntra caloriesIntra;

    String formatPercent = "%.0f%%";
    String formatDistance = "%.2f km";
    String formatCal = "%.0f cal";
    String formatCal2 = "%.0f";

    final private int[] activityColors = {
            Color.parseColor("#FFFF8800"),
            Color.parseColor("#FFFF4444"),
            Color.parseColor("#DEE50B"),
            Color.parseColor("#389105"),
            Color.parseColor("#C3C4C2"),
    };

    SeriesItem bike, run, walk, cal, sum;
    SeriesItem bikeI, runI, walkI, calI;
    SeriesItem background, background2, background3, background4;
    TextView[] activityTexts;
    //For Intra Day Steps
    private LoaderManager.LoaderCallbacks<ResourceLoaderResult<ActivityStepsIntra>> intraStepsLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<ResourceLoaderResult<ActivityStepsIntra>>() {
                @Override
                public Loader<ResourceLoaderResult<ActivityStepsIntra>> onCreateLoader(int i, Bundle bundle) {
                    return ActivityStepsIntraDayService.getDailyActivitySummaryLoader(getActivity(), new Date());
                }

                @Override
                public void onLoadFinished(Loader<ResourceLoaderResult<ActivityStepsIntra>> loader, ResourceLoaderResult<ActivityStepsIntra> activityStepsIntraResourceLoaderResult) {
                    switch (activityStepsIntraResourceLoaderResult.getResultType()) {
                        case ERROR:
                            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                            break;
                        case EXCEPTION:
                            Log.e(TAG, "Error loading data", activityStepsIntraResourceLoaderResult.getException());
                            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                            break;
                        default:
                            stepsIntraData = activityStepsIntraResourceLoaderResult.getResult();
                            loadStepsIntraDayData(stepsIntraData.getActivityStepsIntraDay());
                    }
                }

                @Override
                public void onLoaderReset(Loader<ResourceLoaderResult<ActivityStepsIntra>> loader) {

                }
            };

    //For Intra Day Distance
    private LoaderManager.LoaderCallbacks<ResourceLoaderResult<ActivityDistanceIntra>> intraDistanceLoaderCallBacks =
            new LoaderManager.LoaderCallbacks<ResourceLoaderResult<ActivityDistanceIntra>>() {
                @Override
                public Loader<ResourceLoaderResult<ActivityDistanceIntra>> onCreateLoader(int i, Bundle bundle) {
                    return ActivityDistanceIntraDayService.getDailyActivitySummaryLoader(getActivity(), new Date());
                }

                @Override
                public void onLoadFinished(Loader<ResourceLoaderResult<ActivityDistanceIntra>> loader, ResourceLoaderResult<ActivityDistanceIntra> activityStepsIntraResourceLoaderResult) {
                    switch (activityStepsIntraResourceLoaderResult.getResultType()) {
                        case ERROR:
                            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                            break;
                        case EXCEPTION:
                            Log.e(TAG, "Error loading data", activityStepsIntraResourceLoaderResult.getException());
                            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                            break;
                        default:
                            distanceIntra = activityStepsIntraResourceLoaderResult.getResult();
                            loadDistanceIntraDayData(distanceIntra.getActivityDistanceIntraDay());
                    }
                }

                @Override
                public void onLoaderReset(Loader<ResourceLoaderResult<ActivityDistanceIntra>> loader) {

                }
            };
    //For IntraDay Calories
    private LoaderManager.LoaderCallbacks<ResourceLoaderResult<ActivityCaloriesIntra>> intraCaloriesLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<ResourceLoaderResult<ActivityCaloriesIntra>>() {
                @Override
                public Loader<ResourceLoaderResult<ActivityCaloriesIntra>> onCreateLoader(int i, Bundle bundle) {
                    return ActivityCaloriesIntraDayService.getDailyActivitySummaryLoader(getActivity(), new Date());
                }

                @Override
                public void onLoadFinished(Loader<ResourceLoaderResult<ActivityCaloriesIntra>> loader, ResourceLoaderResult<ActivityCaloriesIntra> activityStepsIntraResourceLoaderResult) {
                    switch (activityStepsIntraResourceLoaderResult.getResultType()) {
                        case ERROR:
                            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                            break;
                        case EXCEPTION:
                            Log.e(TAG, "Error loading data", activityStepsIntraResourceLoaderResult.getException());
                            Toast.makeText(getActivity(), R.string.error_loading_data, Toast.LENGTH_LONG).show();
                            break;
                        default:
                            caloriesIntra = activityStepsIntraResourceLoaderResult.getResult();
                            loadCaloriesIntraDayData(caloriesIntra.getActivityCaloriesIntraDay());
                    }
                }

                @Override
                public void onLoaderReset(Loader<ResourceLoaderResult<ActivityCaloriesIntra>> loader) {

                }
            };

    //For DailyActivitySummary Loader
    private LoaderManager.LoaderCallbacks<ResourceLoaderResult<DailyActivitySummary>> dailyActivitySummaryLoaderCallbacks = new LoaderManager.LoaderCallbacks<ResourceLoaderResult<DailyActivitySummary>>() {
        @Override
        public Loader<ResourceLoaderResult<DailyActivitySummary>> onCreateLoader(int i, Bundle bundle) {
            /*Calendar today = Calendar.getInstance();
            today.set(2017, 04, 15);*/
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Toast.makeText(getContext(), dateFormat.format(new Date()), Toast.LENGTH_SHORT).show();
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

        @Override
        public void onLoaderReset(Loader<ResourceLoaderResult<DailyActivitySummary>> loader) {

        }


    };

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
        goals = new BeFitActivites(10, 5, 2, 3303, (10 + 5 + 2 + 3303));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sumDisplayed = false;

        View view = inflater.inflate(R.layout.fragment_tile_two, container, false);
        activityTexts = new TextView[5];

        decoView = (DecoView) view.findViewById(R.id.dynamicArcView);
        textView = (TextView) view.findViewById(R.id.textPercentage);
        textViewGoals = (TextView) view.findViewById(R.id.textRemaining);
        refreshButton = (ImageButton) view.findViewById(R.id.refreshButton);

        bikeImage = (ImageView) view.findViewById(R.id.bikeActivity1);
        bikeImage.setOnClickListener(this);
        activityTexts[BIKE_INDEX] = (TextView) view.findViewById(R.id.textActivity1);

        sumImage = (ImageView) view.findViewById(R.id.sumActivity5);
        sumImage.setOnClickListener(this);

        runImage = (ImageView) view.findViewById(R.id.runActivity2);
        runImage.setOnClickListener(this);
        activityTexts[RUN_INDEX] = (TextView) view.findViewById(R.id.textActivity2);

        walkImage = (ImageView) view.findViewById(R.id.walkActivity3);
        walkImage.setOnClickListener(this);
        activityTexts[WALK_INDEX] = (TextView) view.findViewById(R.id.textActivity3);

        calImage = (ImageView) view.findViewById(R.id.calActivity4);
        calImage.setOnClickListener(this);
        activityTexts[CAL_INDEX] = (TextView) view.findViewById(R.id.textActivity4);

        createArcs();
        addArcListners();
        getLoaderManager().initLoader(DAILY_ACTIV__LOADER_ID, null, dailyActivitySummaryLoaderCallbacks).forceLoad();
        getLoaderManager().initLoader(INTRA_DAY_STEPS_LOADER_ID, null, intraStepsLoaderCallbacks).forceLoad();
        getLoaderManager().initLoader(INTRA_DAY_DISTANCE_LOADER_ID, null, intraDistanceLoaderCallBacks).forceLoad();
        getLoaderManager().initLoader(INTRA_DAY_CALORIES_LOADER_ID, null, intraCaloriesLoaderCallbacks).forceLoad();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().restartLoader(DAILY_ACTIV__LOADER_ID, null, dailyActivitySummaryLoaderCallbacks);
                getLoaderManager().restartLoader(INTRA_DAY_STEPS_LOADER_ID, null, intraStepsLoaderCallbacks);
            }
        });
        return view;

    }

    public void computeActivityValues() {
        List<Distance> distances = data.getSummary().getDistances();

        for (Distance distance : distances) {
            if (distance.getActivity().equals("Walk")) {
                System.out.println(distance.getDistance());
            }
        }

    }

    public void createArcs() {

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

        sum = new SeriesItem.Builder(activityColors[BIKE_INDEX])
                .setRange(0, goals.sum, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .setInitialVisibility(false)
                .build();

        //For non-inner arcs
        bikeI = new SeriesItem.Builder(activityColors[BIKE_INDEX])
                .setRange(0, goals.bike, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        runI = new SeriesItem.Builder(activityColors[RUN_INDEX])
                .setRange(0, goals.run, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        walkI = new SeriesItem.Builder(activityColors[WALK_INDEX])
                .setRange(0, goals.walk, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

        calI = new SeriesItem.Builder(activityColors[CAL_INDEX])
                .setRange(0, goals.cal, 0)
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_INNER, Color.parseColor("#22000000"), 0.4f))
                .build();

    }

    public void loadData(DailyActivitySummary data) {
        this.data = data;

        if (sumDisplayed == false) {
            sumAction();
            sumDisplayed = true;
            computeActivityValues();

        }
        createNotification(data.getSummary().getCaloriesOut(), data.getGoals().getCaloriesOut());
    }

    public void loadStepsIntraDayData(ActivityStepsIntraDay activityStepsIntraDay) {
        Log.i(TAG, "Loading the Steps Intradata");
        for (DataSetIntraDay dataSetIntraDay : activityStepsIntraDay.getDataSetIntraDays()) {
            Log.i(TAG, dataSetIntraDay.getTime() + " " + dataSetIntraDay.getValue());
        }
        Log.i(TAG, String.valueOf(activityStepsIntraDay.getDataSetInterval()));
        Toast.makeText(getContext(), stepsIntraData.getActivitySteps().toString(), Toast.LENGTH_SHORT).show();
    }

    public void loadCaloriesIntraDayData(ActivityStepsIntraDay activityCaloriesIntraDay) {
        Log.i(TAG, "Loading the Calories Intradata");
        for (DataSetIntraDay dataSetIntraDay : activityCaloriesIntraDay.getDataSetIntraDays()) {
            Log.i(TAG, dataSetIntraDay.getTime() + " " + dataSetIntraDay.getValue());
        }
        Log.i(TAG, String.valueOf(activityCaloriesIntraDay.getDataSetInterval()));
        Toast.makeText(getContext(), caloriesIntra.getActivityCalories().toString(), Toast.LENGTH_SHORT).show();
    }

    public void loadDistanceIntraDayData(ActivityStepsIntraDay activityStepsIntraDay) {
        Log.i(TAG, "Loading the Distance Intradata");
        for (DataSetIntraDay dataSetIntraDay : activityStepsIntraDay.getDataSetIntraDays()) {
            Log.i(TAG, dataSetIntraDay.getTime() + " " + dataSetIntraDay.getValue());
        }
        Log.i(TAG, String.valueOf(activityStepsIntraDay.getDataSetInterval()));
        Toast.makeText(getContext(), distanceIntra.getActivityDistances().toString(), Toast.LENGTH_SHORT).show();
    }


    public void sumAction() {
        //Compute calories percentage
        final Summary summary = data.getSummary();
        final float calBurnt = summary.getCaloriesOut();

        decoView.addSeries(background);
        decoView.addSeries(background2);
        int bikeIndex = decoView.addSeries(bike);
        int runIndex = decoView.addSeries(run);
        int walkIndex = decoView.addSeries(walk);
        int calIndex = decoView.addSeries(cal);
        int sumIndex = decoView.addSeries(sum);

        decoView.addEvent(new DecoEvent.Builder(5).setIndex(bikeIndex).setDelay(1000).build());
        decoView.addEvent(new DecoEvent.Builder((float) (calBurnt + 5 + 0.5 + 0.2)).setIndex(sumIndex).setDelay(500).build());
        decoView.addEvent(new DecoEvent.Builder(0.5f).setIndex(runIndex).setDelay(2000).build());
        decoView.addEvent(new DecoEvent.Builder(0.2f).setIndex(walkIndex).setDelay(3000).build());
        decoView.addEvent(new DecoEvent.Builder(calBurnt).setIndex(calIndex).setDelay(4000).build());
    }

    public void addArcListners() {
        bike.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (formatDistance.contains("km")) {
                    activityTexts[BIKE_INDEX].setTextSize(12);
                    activityTexts[BIKE_INDEX].setText(String.format(formatDistance, currentPosition));

                } else {
                    activityTexts[BIKE_INDEX].setText(String.format(formatDistance, currentPosition));
                }

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        bikeI.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (formatPercent.contains("%%")) {
                    float percentFilled = ((currentPosition - bikeI.getMinValue()) / (bikeI.getMaxValue() - bikeI.getMinValue()));
                    textView.setText(String.format(formatPercent, percentFilled * 100f));

                } else {
                    textView.setText(String.format(formatPercent, currentPosition));
                }

                if (formatDistance.contains("km")) {
                    activityTexts[BIKE_INDEX].setTextSize(12);
                    activityTexts[BIKE_INDEX].setText(String.format(formatDistance, currentPosition));

                } else {
                    activityTexts[BIKE_INDEX].setText(String.format(formatDistance, currentPosition));
                }

                textViewGoals.setText(String.format(formatDistance, currentPosition) + "/" + Float.toString(bikeI.getMaxValue()) + " km");
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        run.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (formatDistance.contains("km")) {
                    activityTexts[RUN_INDEX].setTextSize(12);
                    activityTexts[RUN_INDEX].setText(String.format(formatDistance, currentPosition));

                } else {
                    activityTexts[RUN_INDEX].setText(String.format(formatDistance, currentPosition));
                }

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        runI.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (formatPercent.contains("%%")) {
                    float percentFilled = ((currentPosition - runI.getMinValue()) / (runI.getMaxValue() - runI.getMinValue()));
                    textView.setText(String.format(formatPercent, percentFilled * 100f));

                } else {
                    textView.setText(String.format(formatPercent, currentPosition));
                }

                if (formatDistance.contains("km")) {
                    activityTexts[RUN_INDEX].setTextSize(12);
                    activityTexts[RUN_INDEX].setText(String.format(formatDistance, currentPosition));

                } else {
                    activityTexts[RUN_INDEX].setText(String.format(formatDistance, currentPosition));
                }

                textViewGoals.setText(String.format(formatDistance, currentPosition) + "/" + Float.toString(runI.getMaxValue()) + " km");

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        walk.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                if (formatDistance.contains("km")) {
                    activityTexts[WALK_INDEX].setTextSize(12);
                    activityTexts[WALK_INDEX].setText(String.format(formatDistance, currentPosition));

                } else {
                    activityTexts[WALK_INDEX].setText(String.format(formatDistance, currentPosition));
                }

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        walkI.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                if (formatPercent.contains("%%")) {
                    float percentFilled = ((currentPosition - walkI.getMinValue()) / (walkI.getMaxValue() - walkI.getMinValue()));
                    textView.setText(String.format(formatPercent, percentFilled * 100f));

                } else {
                    textView.setText(String.format(formatPercent, currentPosition));
                }

                if (formatDistance.contains("km")) {
                    activityTexts[WALK_INDEX].setTextSize(12);
                    activityTexts[WALK_INDEX].setText(String.format(formatDistance, currentPosition));

                } else {
                    activityTexts[WALK_INDEX].setText(String.format(formatDistance, currentPosition));
                }

                textViewGoals.setText(String.format(formatDistance, currentPosition) + "/" + Float.toString(walkI.getMaxValue()) + " km");

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        cal.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                if (formatDistance.contains("cal")) {
                    activityTexts[CAL_INDEX].setTextSize(12);
                    activityTexts[CAL_INDEX].setText(String.format(formatCal, currentPosition));

                } else {
                    activityTexts[CAL_INDEX].setTextSize(12);
                    activityTexts[CAL_INDEX].setText(String.format(formatCal, currentPosition));
                }

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
        calI.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                if (formatPercent.contains("%%")) {
                    float percentFilled = ((currentPosition - calI.getMinValue()) / (calI.getMaxValue() - calI.getMinValue()));
                    textView.setText(String.format(formatPercent, percentFilled * 100f));

                } else {
                    textView.setText(String.format(formatPercent, currentPosition));
                }

                if (formatDistance.contains("cal")) {
                    activityTexts[CAL_INDEX].setTextSize(12);
                    activityTexts[CAL_INDEX].setText(String.format(formatCal, currentPosition));

                } else {
                    activityTexts[CAL_INDEX].setTextSize(12);
                    activityTexts[CAL_INDEX].setText(String.format(formatCal, currentPosition));
                }
                textViewGoals.setText(String.format(formatCal2, currentPosition) + "/" + Float.toString(calI.getMaxValue()) + " Cal");

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        sum.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

                textViewGoals.setText("");

                if (formatPercent.contains("%%")) {
                    float percentFilled = ((currentPosition - sum.getMinValue()) / (sum.getMaxValue() - sum.getMinValue()));
                    textView.setText(String.format(formatPercent, percentFilled * 100f));

                } else {
                    textView.setText(String.format(formatPercent, currentPosition));
                }

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
    }

    public void restAction(int activity) {
        final Summary summary = data.getSummary();

        decoView.configureAngles(280, 0);
        decoView.deleteAll();

        decoView.addSeries(background);

        int seriesIndex = 0;

        switch (activity) {
            case BIKE_INDEX:
                seriesIndex = decoView.addSeries(bikeI);
                decoView.addSeries(bikeI);
                break;
            case RUN_INDEX:
                seriesIndex = decoView.addSeries(runI);
                decoView.addSeries(runI);
                break;
            case WALK_INDEX:
                seriesIndex = decoView.addSeries(walkI);
                decoView.addSeries(walkI);
                break;
            case CAL_INDEX:
                seriesIndex = decoView.addSeries(calI);
                decoView.addSeries(calI);
                break;
        }

        float percent = 0;

        switch (activity) {
            case BIKE_INDEX:
                percent = 5;
                break;
            case RUN_INDEX:
                percent = 0.5f;
                break;
            case WALK_INDEX:
                percent = 0.2f;
                break;
            case CAL_INDEX:
                percent = summary.getCaloriesOut();
                break;
        }

        decoView.addEvent(new DecoEvent.Builder(percent).setIndex(seriesIndex).setDelay(1000).build());

    }


    public void createNotification(double burnedCalories, double targetCalories) {
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

        //Log.i("FragmentTileTwo", String.valueOf(data.getActivities().get(0)));
        List<Distance> distances = data.getSummary().getDistances();
        Distance distance = distances.get(0);
        for (Distance distance1 : distances) {
            Log.i("FragmentTileTwo", String.valueOf(distance1.getActivity() + " " + distance1.getDistance()));
        }

/*
        notificationView.setTextViewText(R.id.status_bar_track_name, (String) distanceIntra.getActivityDistances().toString());
        bigNotificationView.setTextViewText(R.id.status_bar_track_name, (String) distanceIntra.getActivityDistances().toString());
*/

        notificationView.setTextViewText(R.id.status_bar_artist_name, String.valueOf(targetCalories));
        bigNotificationView.setTextViewText(R.id.status_bar_artist_name, String.valueOf(targetCalories));

        bigNotificationView.setTextViewText(R.id.status_bar_album_name, "CALORIES BURNT");

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
    public void onClick(View v) {
        if (v.getId() == bikeImage.getId()) {
            restAction(BIKE_INDEX);
        } else if (v.getId() == sumImage.getId()) {
            sumAction();
        } else if (v.getId() == runImage.getId()) {
            restAction(RUN_INDEX);
        } else if (v.getId() == walkImage.getId()) {
            restAction(WALK_INDEX);
        } else if (v.getId() == calImage.getId()) {
            restAction(CAL_INDEX);
        }
    }
}