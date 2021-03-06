package api.services;

import android.app.Activity;
import android.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.fitbit.authentication.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import api.exceptions.MissingScopesException;
import api.exceptions.TokenExpiredException;
import api.loaders.ResourceLoaderResult;
import api.loaders.ResourceLoaderFactory;
import api.models.DailyActivitySummary;

/**
 * Created by jboggess on 10/3/16.
 */
public class ActivityService {

    private final static String ACTIVITIES_URL = "https://api.fitbit.com/1/user/-/activities/date/%s.json";
    private static final ResourceLoaderFactory<DailyActivitySummary> USER_ACTIVITIES_LOADER_FACTORY = new ResourceLoaderFactory<>(ACTIVITIES_URL, DailyActivitySummary.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static Loader<ResourceLoaderResult<DailyActivitySummary>> getDailyActivitySummaryLoader(Activity activityContext, Date date) throws MissingScopesException, TokenExpiredException {
        /*Calendar today = Calendar.getInstance();
        today.set(2017, 04, 10);*/
        return USER_ACTIVITIES_LOADER_FACTORY.newResourceLoader(activityContext, new Scope[]{Scope.activity}, dateFormat.format(date));
    }

}
