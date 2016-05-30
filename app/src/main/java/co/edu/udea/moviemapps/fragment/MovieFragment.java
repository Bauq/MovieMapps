package co.edu.udea.moviemapps.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.Pelicula;
import co.edu.udea.moviemapps.rest.MovieMappsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Brian on 05/05/2016.
 */
public class MovieFragment extends Fragment {
    public static final int ID = 2;
    public static final String ARG_ID_PELICULA = "idPelicula";
    public String apiKey = "d4aadc42b63f7a1565bffa6dd41f1bfc";

    public ImageView posterPelicula;
    public TextView tituloPelicula, descripcionPelicula;
    private String idPelicula;

    public static MovieFragment newInstance(String idMovie) {
        MovieFragment fragment = new MovieFragment();
        fragment.idPelicula = idMovie;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPelicula = getArguments().getString(ARG_ID_PELICULA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posterPelicula = (ImageView) view.findViewById(R.id.iv_poster_movie);
        descripcionPelicula = (TextView) view.findViewById(R.id.tv_overview);
        tituloPelicula = (TextView) view.findViewById(R.id.tv_title);
        new DownloadMovie().execute();

    }

    private class DownloadMovie extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            final Bitmap imagen;
            Call<Pelicula> resultados = MovieMappsService.obtenerInstancia().movieById(idPelicula, apiKey);
            resultados.enqueue(new Callback<Pelicula>() {
                @Override
                public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                    setMovie(response.body());
                }

                @Override
                public void onFailure(Call<Pelicula> call, Throwable t) {

                }

            });
            return response;
        }
    }

    public void setMovie(Pelicula pelicula) {
        if (pelicula != null) {
            tituloPelicula.setText(pelicula.getTitle());
            descripcionPelicula.setText(pelicula.getOverview());
            downloadImage(posterPelicula, pelicula.getPosterPath());
        }
    }

    private void downloadImage(final ImageView imageView, String url) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String BASEURLIMAGEN = "http://image.tmdb.org/t/p/w500";
                URL imageUrl;
                Bitmap imagen = null;
                try {
                    imageUrl = new URL(BASEURLIMAGEN + params[0]);
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    imagen = BitmapFactory.decodeStream(conn.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return imagen;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute(url);
    }
}
