package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.MovieMapps;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.R;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Usuario;
import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.persistence.UsuarioDataManager;
import retrofit2.Call;

/**
 * Created by Sebastian on 11/04/2016.
 */
public class FragmentLogin extends Fragment {
    public static final int ID = 3;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    Toast toast;
    TextView tvNombre;
    ImageView ivImagen;
    Usuario usuario;
    public static FragmentLogin newInstance(){
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    private void mostrarUsuario(Profile currentProfile) {
        usuario = MovieMapps.getUsuario();
        if(usuario != null){
            asyncTask.execute(usuario.getFoto());
            toast = Toast.makeText(MovieMapps.getContext(), usuario.getNombre(), Toast.LENGTH_LONG);
            toast.show();
            tvNombre.setText(usuario.getNombre());
        }
        else if (currentProfile != null) {
            usuario = new Usuario();
            usuario.setNombre(currentProfile.getName());
            Uri uri = currentProfile.getProfilePictureUri(200, 200);
            asyncTask.execute(uri.toString());
            usuario.setFoto(uri.toString());
            UsuarioDataManager.getInstance().update(usuario);
            MovieMapps.setUsuario(usuario);
            toast = Toast.makeText(MovieMapps.getContext(), usuario.getNombre(), Toast.LENGTH_LONG);
            toast.show();
        }

    }
    private void actualizarUsuario(Usuario body) {
        usuario = body;
        UsuarioDataManager.getInstance().update(usuario);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FacebookSdk.sdkInitialize(MovieMapps.getContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        tvNombre = (TextView) view.findViewById(R.id.nombre);
        ivImagen = (ImageView) view.findViewById(R.id.imagen);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    usuario = MovieMapps.getUsuario();
                                    usuario.setId(Long.valueOf(object.getString("id")));
                                    usuario.setCorreo(object.getString("email"));
                                    MovieMapps.setUsuario(usuario);
                                    actualizarUsuario(usuario);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                Log.i("login","loggeado");
            }

            @Override
            public void onCancel() {
                Log.i("login","cancelado");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("login",exception.getMessage());
            }
        });
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                mostrarUsuario(oldProfile);
                mostrarUsuario(currentProfile);
            }
        };
           }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    AsyncTask<String, Void, Bitmap> asyncTask = new AsyncTask<String, Void, Bitmap>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            URL imageUrl;
            Bitmap imagen = null;
            try {
                imageUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                Toast.makeText(MovieMapps.getContext(), "Error cargando la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ivImagen.setImageBitmap(bitmap);
            this.cancel(true);
        }
    };

}
