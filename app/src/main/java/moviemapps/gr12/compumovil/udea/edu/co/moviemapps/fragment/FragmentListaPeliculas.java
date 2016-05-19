package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.listener.OnFragmentInteractionListener;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.listener.OnItemMovieListener;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.MyAdapter;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.R;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest.MovieMappsService;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest.Resultado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Samuel on 11/04/2016.
 */
public class FragmentListaPeliculas extends Fragment implements OnItemMovieListener {
    public static final int ID = 1;

    private RecyclerView recyclerView;
    private String peliculasApiKey = getResources().getString(R.string.api_key);
    private LinearLayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private List<Movie> movies;

    public static FragmentListaPeliculas newInstance(){
        return new FragmentListaPeliculas();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_pelicula, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);
        new Download().execute();
        mListener = (OnFragmentInteractionListener) getActivity();
    }

    @Override
    public void onItemClick(Movie movie, View view, int position) {
            Bundle bundle = new Bundle();
            bundle.putString(MovieFragment.ARG_PARAM1,String.valueOf(movie.getId()));
            mListener.setFragment(MovieFragment.ID, bundle,true);
    }

    @Override
    public void onItemLongClick(Movie movie, View view, int position) {

    }

    private class Download extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Call<Resultado> resultados = MovieMappsService.obtenerInstancia().top_peliculas(peliculasApiKey);
            resultados.enqueue(new Callback<Resultado>() {
                @Override
                public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                    setMovies(response.body());
                }

                @Override
                public void onFailure(Call<Resultado> call, Throwable t) {

                }
            });

            return null;
        }
    }

    public boolean setMovies(Resultado resultado){
        if(resultado != null) {
            movies = resultado.getResults();
            MyAdapter adapter = new MyAdapter((ArrayList<Movie>) movies,this);
            recyclerView.setAdapter(adapter);
            return true;
        }
        return false;
    }

    @Test
    public void movieValidator() {
        assertThat(setMovies((Resultado) movies), is(true));
    }
}
