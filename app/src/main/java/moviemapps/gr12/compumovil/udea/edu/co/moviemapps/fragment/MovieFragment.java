package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.R;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest.MovieMappsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Brian on 05/05/2016.
 */
public class MovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final int ID = 2;
    public String apiKey = "d4aadc42b63f7a1565bffa6dd41f1bfc";
    public static final String ARG_PARAM1 = "id";
    public ImageView ivImage;
    public TextView tvTitle, tvOverview;
    public Movie movie;


    // TODO: Rename and change types of parameters
    private String id;


    private OnFragmentInteractionListener mListener;

    public MovieFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1) {
        MovieFragment fragment = new MovieFragment();
        fragment.id = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImage = (ImageView) view.findViewById(R.id.iv_poster_movie);
        tvOverview = (TextView) view.findViewById(R.id.tv_overview);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        new DownloadMovie().execute();

    }


    private class DownloadMovie extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            final Bitmap imagen;
            Call<Movie> resultados = MovieMappsService.obtenerInstancia().movieById(id, apiKey);
            resultados.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    setMovie(response.body());

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                }

            });

            return response;
        }
    }


    public void setMovie(Movie movie) {
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText("Overview: \n" + movie.getOverview());
            downloadImage(ivImage, movie.getPosterPath());

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
