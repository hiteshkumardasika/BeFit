package api.services;

import android.app.Activity;
import android.content.Loader;

import com.fitbit.authentication.Scope;

import api.exceptions.MissingScopesException;
import api.exceptions.TokenExpiredException;
import api.loaders.ResourceLoaderFactory;
import api.loaders.ResourceLoaderResult;
import api.models.Device;


/**
 * Created by jboggess on 9/14/16.
 */
public class DeviceService {

    private final static String DEVICE_URL = "https://api.fitbit.com/1/user/-/devices.json";
    private static final ResourceLoaderFactory<Device[]> USER_DEVICES_LOADER_FACTORY = new ResourceLoaderFactory<>(DEVICE_URL, Device[].class);

    public static Loader<ResourceLoaderResult<Device[]>> getUserDevicesLoader(Activity activityContext) throws MissingScopesException, TokenExpiredException {
        return USER_DEVICES_LOADER_FACTORY.newResourceLoader(activityContext, new Scope[]{Scope.settings});
    }

}
