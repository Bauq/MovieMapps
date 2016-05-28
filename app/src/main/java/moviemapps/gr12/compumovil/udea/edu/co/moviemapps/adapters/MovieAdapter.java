package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.adapters;

/**
 * Created by Carolina on 11/04/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.listener.OnItemMovieListener;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Pelicula;

import static moviemapps.gr12.compumovil.udea.edu.co.moviemapps.R.*;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Pelicula> listMovies;
    private OnItemMovieListener listener;


    public MovieAdapter(ArrayList<Pelicula> movies, OnItemMovieListener listener) {
        listMovies = movies;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout.adapter_pelicula, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (listMovies.get(position) != null) {
            if (listMovies.get(position).getPosterPath() != null) {
                downloadImage(holder, listMovies.get(position).getPosterPath());
            }
            holder.nombrePelicula.setText(listMovies.get(position).getTitle());
            holder.duracionPelicula.setText(String.valueOf(listMovies.get(position).getReleaseDate()));

            holder.nombrePelicula.setSelected(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(listMovies.get(position), holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePelicula, duracionPelicula;
        ImageView posterPelicula;

        public ViewHolder(View itemView) {
            super(itemView);
            posterPelicula = (ImageView) itemView.findViewById(id.iv_poster);
            nombrePelicula = (TextView) itemView.findViewById(id.tv_nombre_pelicula);
            duracionPelicula = (TextView) itemView.findViewById(id.tv_duracion);

        }
    }

    private void downloadImage(final ViewHolder viewHolder, String urlImagen) {
        AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {

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
                viewHolder.posterPelicula.setImageBitmap(bitmap);
            }
        };
        asyncTask.execute(urlImagen);
    }

}
