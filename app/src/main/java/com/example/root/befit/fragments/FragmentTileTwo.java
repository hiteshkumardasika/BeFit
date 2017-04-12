package com.example.root.befit.fragments;

/**
 * Created by root on 3/30/17.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.befit.R;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.Date;

import android.content.Loader;
import android.app.LoaderManager;
import android.widget.TextView;
import android.widget.Toast;

import api.models.DailyActivitySummary;
import api.models.Goals;
import api.models.Summary;
import api.services.ActivityService;
import api.loaders.ResourceLoaderResult;


public class FragmentTileTwo extends Fragment implements LoaderManager.LoaderCallbacks<ResourceLoaderResult<DailyActivitySummary>> {

    DecoView decoView;
    TextView textView;
    int backIndex = 0;
    protected final String TAG = getClass().getSimpleName();
    private static final int FRAGMENT_LOADER_ID = 1;

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
        SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 50, 0)
                .build();
        backIndex = decoView.addSeries(seriesItem);
        decoView.addSeries(seriesItem);
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

    public void loadData(DailyActivitySummary data) {
        final Summary summary = data.getSummary();
        final Goals goals = data.getGoals();
        Toast.makeText(getActivity(), "Calories burnt" + summary.getCaloriesOut()+" goal was"+goals.getCaloriesOut(), Toast.LENGTH_LONG).show();
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                .setRange(0, summary.getCaloriesOut(), 0)
                .build();
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float v, float v1) {
                float percentFilled = ((summary.getCaloriesOut() - 0) / (goals.getCaloriesOut() - 0));
                textView.setText(String.format("%.0f%%", percentFilled * 100f));
            }

            @Override
            public void onSeriesItemDisplayProgress(float v) {

            }
        });
        int i = decoView.addSeries(seriesItem);
        decoView.addEvent(new DecoEvent.Builder(goals.getCaloriesOut())
                .setIndex(backIndex)
                .setDuration(3000)
                .setDelay(100)
                .build());
        decoView.addEvent(new DecoEvent.Builder(16.3f)
                .setIndex(i)
                .setDelay(5000)
                .build());

    }


    @Override
    public void onLoaderReset(Loader<ResourceLoaderResult<DailyActivitySummary>> loader) {

    }

}