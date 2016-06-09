package co.edu.udea.moviemapps.rest;


import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieMappsServiceI {

    @POST("api/calificacions")
    Call<ServiceResult> saveClassification(@Body Classification classification);

    @POST("api/usuarios")
    Call<ServiceResult> saveUser(@Body User user);

    @GET("api/calificacions")
    Call<ServiceResult> getClassificationMovie(@Query("filter[where][idMovie]") String idMovie);
}
