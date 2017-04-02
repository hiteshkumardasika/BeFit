package api.services;

import android.app.Activity;
import android.content.Loader;

import com.fitbit.authentication.Scope;

import api.exceptions.MissingScopesException;
import api.exceptions.TokenExpiredException;
import api.loaders.ResourceLoaderFactory;
import api.loaders.ResourceLoaderResult;
import api.models.UserContainer;

/**
 * Created by jboggess on 9/14/16.
 */
public class UserService {

    private final static String USER_URL = "https://api.fitbit.com/1/user/-/profile.json";
    private static final ResourceLoaderFactory<UserContainer> USER_PROFILE_LOADER_FACTORY = new ResourceLoaderFactory<>(USER_URL, UserContainer.class);

    public static Loader<ResourceLoaderResult<UserContainer>> getLoggedInUserLoader(Activity activityContext) throws MissingScopesException, TokenExpiredException {
        return USER_PROFILE_LOADER_FACTORY.newResourceLoader(activityContext, new Scope[]{Scope.profile});
    }

}
