package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Samuel Arenas
 * @version 1.0.0
 * @date 05/08/2015
 */
public class MovieMappsService {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MovieMappsServiceI instance;

    public static MovieMappsServiceI obtenerInstancia() {
        if (instance == null) {
            Interceptor requestInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().build();
                    return chain.proceed(newRequest);
                }
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(requestInterceptor);
            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            instance = retrofit.create(MovieMappsServiceI.class);
        }
        return instance;
    }
}
