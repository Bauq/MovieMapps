package co.edu.udea.moviemapps.fragment;

import android.content.Intent;
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


import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.model.User;
import co.edu.udea.moviemapps.persistence.UserDataManager;


public class Login extends Fragment {
    public static final int ID = 3;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    Toast toast;
    TextView userName;
    ImageView userProfilePicture;
    User user;
    MovieMapps movieMapps = new MovieMapps();

    public static Login newInstance() {
        return new Login();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }

    private void showUser(Profile currentProfile) {
        user = movieMapps.getUser();
        if (user != null) {
            downloadImage.execute(user.getPhoto());
            userName.setText(user.getName());
            toast = Toast.makeText(MovieMapps.getContext(), user.getName(), Toast.LENGTH_LONG);
            toast.show();
        } else if (currentProfile != null) {
            user = new User();
            user.setName(currentProfile.getName());
            Uri profilePictureUri = currentProfile.getProfilePictureUri(200, 200);
            downloadImage.execute(profilePictureUri.toString());
            user.setPhoto(profilePictureUri.toString());
            updateUser(user);
            toast = Toast.makeText(MovieMapps.getContext(), user.getName(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void updateUser(User user) {
        this.user = user;
        movieMapps.setUser(user);
        UserDataManager.getInstance().update(this.user);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FacebookSdk.sdkInitialize(MovieMapps.getContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        userName = (TextView) view.findViewById(R.id.name);
        userProfilePicture = (ImageView) view.findViewById(R.id.image);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    user = movieMapps.getUser();
                                    user.setId(Integer.valueOf(object.getString("id")));
                                    user.setEmail(object.getString("email"));
                                    updateUser(user);
                                } catch (JSONException e) {
                                    Log.e("ERROR", "JSON parse failed at onCompleted: ", e);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("login", "Canceled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("login", exception.getMessage());
            }
        });
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                showUser(oldProfile);
                showUser(currentProfile);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    AsyncTask<String, Void, Bitmap> downloadImage = new AsyncTask<String, Void, Bitmap>() {
        @Override
        protected Bitmap doInBackground(String... params) {

            URL imageUrl;
            Bitmap image = null;
            try {
                imageUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                image = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                Log.e("ERROR", "Load image failed at doInBackground: ", e);
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            userProfilePicture.setImageBitmap(bitmap);
            this.cancel(true);
        }
    };
}
