package com.wnp.lqmall.http;

import android.os.Build;
import android.provider.SyncStateContract;

import com.simplexx.wnp.baselib.Config;
import com.simplexx.wnp.baselib.ui.ClientSettings;
import com.simplexx.wnp.baselib.util.GsonUtils;
import com.simplexx.wnp.baselib.util.StringUtil;
import com.simplexx.wnp.users.Constants;
import com.simplexx.wnp.util.DeviceUtil;
import com.simplexx.wnp.util.executor.ThreadExecutor;
import com.wnp.lqmall.BuildConfig;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class HttpApiProxyCreate {
    private class ApiConfig {
        public final String host;
        public final boolean useIp;
        public final String baseUrl;
        public final int writeTimeoutSeconds;
        public final boolean includeCookie;

        private ApiConfig(String baseUrl, int writeTimeoutSeconds, boolean useIp, boolean includeCookie) {
            this.baseUrl = baseUrl;
            this.writeTimeoutSeconds = writeTimeoutSeconds;
            this.host = URI.create(baseUrl).getHost();
            this.useIp = useIp;
            this.includeCookie = includeCookie;
        }
    }

    private class GsonConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody value) throws IOException {
                    return GsonUtils.tryParse(type, value.string());
                }
            };
        }
    }

    private class AsyncCallAdapterFactory extends CallAdapter.Factory {
        private class AsyncCall<T> implements Call<T> {
            private final Call<T> internalCall;
            private retrofit2.Response<T> response;
            private IOException ioException = null;
            private RuntimeException runtimeException = null;

            public AsyncCall(Call<T> internalCall) {
                this.internalCall = internalCall;
            }

            @Override
            public retrofit2.Response<T> execute() throws IOException {
                if (!isExecuted()) {
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                retrofit2.Response<T> r = internalCall.execute();
                                response = r;
                            } catch (IOException e) {
                                ioException = e;
                            } catch (RuntimeException e) {
                                runtimeException = e;
                            } finally {
                                synchronized (this) {
                                    notifyAll();
                                }
                            }
                        }
                    };
                    ThreadExecutor.runInAsync(runnable);
                    synchronized (runnable) {
                        try {
                            runnable.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (ioException != null || runtimeException != null)
                    if (ioException != null)
                        throw ioException;
                if (runtimeException != null)
                    throw runtimeException;
                return response;
            }

            @Override
            public void enqueue(Callback<T> callback) {
                internalCall.enqueue(callback);
            }

            @Override
            public boolean isExecuted() {
                return internalCall.isExecuted();
            }

            @Override
            public void cancel() {
                internalCall.cancel();
            }

            @Override
            public boolean isCanceled() {
                return internalCall.isCanceled();
            }

            @Override
            public Call<T> clone() {
                return new AsyncCall<>(internalCall.clone());
            }

            @Override
            public Request request() {
                return internalCall.request();
            }
        }

        private class AsyncAdapter implements CallAdapter<AsyncCall> {
            private final Type responseType;

            public AsyncAdapter(Type responseType) {
                this.responseType = responseType;
            }

            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public <R> AsyncCall<R> adapt(Call<R> call) {
                return new AsyncCall<>(call);
            }
        }

        @Override
        public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalStateException(
                        "MyCall must have generic type (e.g., MyCall<ResponseBody>)");
            }
            Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new AsyncAdapter(responseType);
        }
    }

    public static String getCheckClientVersionUrl(String version) {
        return Config.COMMON_DATA_BASE_URL + "/Api/ClientVersion?os=0&version=" + version;
    }

    private static final CookieManager cookieManager = new CookieManager();
    private static final ConcurrentHashMap<String, Retrofit> retrofitHashMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Object> clientHashMap = new ConcurrentHashMap<>();

    public HttpApiProxyCreate() {
    }

    public <T> T create(Class<T> classOfT) {
        String identity = classOfT.getName();
        Object client = clientHashMap.get(identity);
        if (client == null) {
            ApiConfig config = getConfig(classOfT);
            String baseUrl = Config.getBaseUrl(config.host);
            Retrofit retrofit = retrofitHashMap.get(config.baseUrl);
            if (retrofit == null) {
                retrofitHashMap.put(config.baseUrl, retrofit = createRetrofit(config, baseUrl));
            }
            clientHashMap.put(identity, client = retrofit.create(classOfT));
        }
        return (T) client;
    }

    /**
     * 用于切换url站点，根据传进来的ApiService的className做判断
     * 也可以作为切换正式站点或者测试站点
     *
     * @param classOfT
     * @param <T>
     * @return
     */
    private <T> ApiConfig getConfig(Class<T> classOfT) {
        return new ApiConfig(Config.URL_P, 15, true, true);
    }

    private Retrofit createRetrofit(ApiConfig config, String baseUrl) {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(config.writeTimeoutSeconds, TimeUnit.SECONDS)
                .addInterceptor(new RequestHeaderInterceptor(config))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                        HttpLoggingInterceptor.Level.NONE));
        if (config.includeCookie)
            httpBuilder.cookieJar(new JavaNetCookieJar(cookieManager));

        OkHttpClient httpClient = httpBuilder.build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .callFactory(httpClient)
                .addCallAdapterFactory(new AsyncCallAdapterFactory())
                .addConverterFactory(new GsonConverterFactory())
                .baseUrl(StringUtil.firstOrDefaultNotNullOrWhiteSpace(baseUrl, config.baseUrl));

        Retrofit retrofit = builder.build();
        return retrofit;
    }

    private static class RequestHeaderInterceptor implements Interceptor {
        private final ApiConfig config;

        private RequestHeaderInterceptor(ApiConfig config) {
            this.config = config;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            ClientSettings clientSettings = new ClientSettings();
            Request.Builder builder = chain
                    .request()
                    .newBuilder();
            if (config.useIp) {
                builder.addHeader("Host", config.host);
            }
            Request request = builder
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .addHeader("Versions", Constants.API_VERSIONS)
                    .addHeader("User-Agent", "Client-Android-" + clientSettings.getVersion() + "-" + DeviceUtil.getDeviceModel() + "-" +
                            DeviceUtil.getDeviceName())
                    .build();
            return chain.proceed(request);
        }
    }
}
