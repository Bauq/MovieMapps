package co.edu.udea.moviemapps.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;
import co.edu.udea.moviemapps.listener.OnItemMovieListener;

import co.edu.udea.moviemapps.adapters.MovieAdapter;
import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.Pelicula;
import co.edu.udea.moviemapps.rest.MovieMappsService;
import co.edu.udea.moviemapps.model.Resultado;
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
    private String peliculasApiKey;
    private LinearLayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private List<Pelicula> movies;

    public static FragmentListaPeliculas newInstance(){
        return new FragmentListaPeliculas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_pelicula, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        peliculasApiKey = getResources().getString(R.string.api_key);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        new downloadMovieList().execute();
        mListener = (OnFragmentInteractionListener) getActivity();
    }

    @Override
    public void onItemClick(Pelicula pelicula, View view, int position) {
            Bundle bundle = new Bundle();
            bundle.putString(MovieFragment.ARG_ID_PELICULA,String.valueOf(pelicula.getId()));
            mListener.setFragment(MovieFragment.ID, bundle,true);
    }

    private class downloadMovieList extends AsyncTask<Void, Void, Response> {

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
            MovieAdapter adapter = new MovieAdapter((ArrayList<Pelicula>) movies,this);
            recyclerView.setAdapter(adapter);
            return true;
        }
        return false;
    }
}
