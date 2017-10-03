package udacity.movieapp.network;

import java.util.Map;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class RequestManager {
    /* URLs
* NOTE: Must end with a forward slash '/'
* */
    private static final String BASE_URL = BuildConfig.BASE_URL;

    private static RequestManager mRequestManager;

    private Map<String, String> params;

    private RequestManager() { /* Empty constructor */ }

    private RequestManager(Map<String, String> params) {
        this.params = params;
    }
    public static RequestManager getInstance(){
        return getInstance(null);
    }

    public static synchronized RequestManager getInstance(Map<String, String> params){
        if (mRequestManager == null){
            mRequestManager = new RequestManager(params);
        }
        return mRequestManager;
    }
    public <T> T startRequest(Class<T> mClass) {
        return new ApiClient(generateRequestConfig()).createService(mClass);
    }

    public <T> T startRequest(Class<T> mClass,RequestConfig requestConfig) {
        return new ApiClient(requestConfig).createService(mClass);
    }
    public RequestConfig generateRequestConfig() {
        RequestConfig commonConfig = new RequestConfig() ;
        if (params != null)
            commonConfig.addParams(params);
        // Allowing children classes to update the config object, couple of things are to be noted here
        // 1- This is done before adding the headers interceptor so that it includes all headers (common and child's)
        // 2- Any Converters added by children will be added first and has priority over the default converter
        // 3- Any CallAdapters added by children will be added first and has priority over default call adapter
        commonConfig = updateRequestConfig(commonConfig);
        // Adding default converters and call adapters
        commonConfig.addConverterFactory(GsonConverterFactory.create());
        commonConfig.addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()));
        return commonConfig;
    }
    private RequestConfig updateRequestConfig(RequestConfig requestConfig) {
        return requestConfig;
    }

}
