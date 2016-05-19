package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest;


import java.util.List;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Samuel Arenas
 * @version 1.0.0
 * @date 04/2016
 */
public interface MovieMappsServiceI {

    @GET("movie/now_playing")
    Call<Resultado> top_peliculas(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> movieById(@Path("id") String id,
                          @Query("api_key") String apiKey);

}
