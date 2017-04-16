package api.services;

import android.app.Activity;
import android.content.Loader;

import com.fitbit.authentication.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import api.exceptions.MissingScopesException;
import api.exceptions.TokenExpiredException;
import api.loaders.ResourceLoaderFactory;
import api.loaders.ResourceLoaderResult;
import api.models.ActivityCaloriesIntra;
import api.models.ActivityStepsIntra;

/**
 * Created by root on 4/16/17.
 */

public class ActivityCaloriesIntraDayService {
    private final static String ACTIVITIES_URL = "https://api.fitbit.com/1/user/-/activities/calories/date/%s/1d/15min.json";
    private static final ResourceLoaderFactory<ActivityCaloriesIntra> USER_ACTIVITIES_LOADER_FACTORY = new ResourceLoaderFactory<>(ACTIVITIES_URL, ActivityCaloriesIntra.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static Loader<ResourceLoaderResult<ActivityCaloriesIntra>> getDailyActivitySummaryLoader(Activity activityContext, Date date) throws MissingScopesException, TokenExpiredException {
        /*Calendar today = Calendar.getInstance();
        today.set(2017, 04, 10);*/
        return USER_ACTIVITIES_LOADER_FACTORY.newResourceLoader(activityContext, new Scope[]{Scope.activity}, dateFormat.format(date));
    }

}
