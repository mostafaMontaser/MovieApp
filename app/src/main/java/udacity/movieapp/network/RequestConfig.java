package udacity.movieapp.network;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import udacity.movieapp.util.BuildConfig;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class RequestConfig {
    private static final int TIMEOUT_DEFAULT_VALUE = 20;

    private String baseUrl;

    private int connectionTimeout = TIMEOUT_DEFAULT_VALUE;
    private int readTimeout = TIMEOUT_DEFAULT_VALUE;
    private int writeTimeout = TIMEOUT_DEFAULT_VALUE;

    private Map<String, String> headers;
    private Map<String, String> params;

    private List<Interceptor> interceptors = new ArrayList<>();
    private List<Converter.Factory> converterFactories = new ArrayList<>();
    private List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>();

    /* Timeout values in seconds */
    protected static final int CONNECTION_TIMEOUT = 20;
    protected static final int READ_TIMEOUT = 20;
    protected static final int WRITE_TIMEOUT = 20;

    public RequestConfig() {
        this(BuildConfig.BASE_URL
                , CONNECTION_TIMEOUT
                , READ_TIMEOUT, WRITE_TIMEOUT
                , new HashMap<String, String>()
                , new HashMap<String, String>());
    }

    //region Constructors
    public RequestConfig(String baseUrl) {
        this(baseUrl, new HashMap<String, String>(0));
    }

    public RequestConfig(String baseUrl, Map<String, String> headers) {
        this(baseUrl, TIMEOUT_DEFAULT_VALUE, TIMEOUT_DEFAULT_VALUE, TIMEOUT_DEFAULT_VALUE, headers, new HashMap<String, String>(0));
    }

    public RequestConfig(String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout) {
        this(baseUrl, connectionTimeout, readTimeout, writeTimeout, new HashMap<String, String>(0));
    }

    public RequestConfig(String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout, Map<String, String> headers) {
        this(baseUrl, connectionTimeout, readTimeout, writeTimeout, headers, new HashMap<String, String>(0));
    }

    public RequestConfig(String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout,
                         Map<String, String> headers, Map<String, String> params) {
        verify(baseUrl, connectionTimeout, readTimeout, writeTimeout);
        this.baseUrl = baseUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.headers = headers == null ? new HashMap<String, String>(0) : headers;
        this.params = params == null ? new HashMap<String, String>(0) : params;
    }
    //endregion

    private void verify(String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout) {
        if (TextUtils.isEmpty(baseUrl))
            throwInvalidParameter("Base URL cannot be null or empty!");

        if (connectionTimeout < 0)
            throwInvalidParameter("Connection timeout cannot be less than zero!");

        if (readTimeout < 0)
            throwInvalidParameter("Read timeout cannot be less than zero!");

        if (writeTimeout < 0)
            throwInvalidParameter("Write timeout cannot be less than zero!");
    }

    private void throwInvalidParameter(String msg) {
        throw new IllegalArgumentException(msg);
    }


    //region Adding custom values to network executor
    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void addConverterFactory(Converter.Factory factory) {
        converterFactories.add(factory);
    }

    public void addCallAdapterFactory(CallAdapter.Factory factory) {
        callAdapterFactories.add(factory);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addParams(Map<String, String> params) {
        this.params.putAll(params);
    }
    //endregion


    //region Getters
    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    int getWriteTimeout() {
        return writeTimeout;
    }

    int getReadTimeout() {
        return readTimeout;
    }

    int getConnectionTimeout() {
        return connectionTimeout;
    }

    String getBaseUrl() {
        return baseUrl;
    }

    List<CallAdapter.Factory> getCallAdapterFactories() {
        return callAdapterFactories;
    }

    List<Converter.Factory> getConverterFactories() {
        return converterFactories;
    }

    List<Interceptor> getInterceptors() {
        return interceptors;
    }
    //endregion


    //region Setters
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    //endregion

    public String getDefaultBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    protected Map<String, String> getCommonHeaders() {
        // TODO add common headers
        Map<String, String> headers = new HashMap<>();
        return headers;
    }

    private Map<String, String> getCommonParams() {
        // TODO add common params
        Map<String, String> params = new HashMap<>();
        return params;
    }
}
