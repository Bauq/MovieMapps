package co.edu.udea.moviemapps.rest;


import co.edu.udea.moviemapps.model.Classification;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface MovieMappsServiceI {

    @POST("api/Calificacions")
    Call<ServiceResult> saveClassification(@Body Classification classification);
    @POST("api/Usuarios")
    Call<ServiceResult> saveUser(@Body User user);
}
