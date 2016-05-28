package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest;


import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<Pelicula> movieById(@Path("id") String id,
                          @Query("api_key") String apiKey);

}
