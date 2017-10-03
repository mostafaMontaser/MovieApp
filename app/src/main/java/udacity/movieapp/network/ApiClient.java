package udacity.movieapp.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Mostafa Montaser on 1/22/2017.
 */

public class ApiClient {
    private final RequestConfig requestConfig;

    private final Retrofit retrofit;

    public ApiClient(RequestConfig config) {
        requestConfig = config;
        retrofit = retrofit(okHttpClient());
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * @return A configured {@link OkHttpClient} that will be used for executing network requests
     */
    private OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // Add custom interceptors from config
        for (Interceptor interceptor : requestConfig.getInterceptors()) {
            builder.addInterceptor(interceptor);
        }

        // Add timeouts
        builder.connectTimeout(requestConfig.getConnectionTimeout(), TimeUnit.SECONDS)
                .readTimeout(requestConfig.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(requestConfig.getWriteTimeout(), TimeUnit.SECONDS);

        return builder.build();
    }

    /**
     * @param client The client that will be used for executing network requests
     * @return A configured {@link Retrofit} instance that can be used to create services interfaces
     */
    private Retrofit retrofit(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(requestConfig.getBaseUrl())
                .client(client);

        // Add custom converters (parsers)
        for (Converter.Factory converterFactory : requestConfig.getConverterFactories()) {
            builder.addConverterFactory(converterFactory);
        }

        // Add custom call adapter factories
        for (CallAdapter.Factory callAdapterFactory : requestConfig.getCallAdapterFactories()) {
            builder.addCallAdapterFactory(callAdapterFactory);
        }

        return builder.build();
    }

}
