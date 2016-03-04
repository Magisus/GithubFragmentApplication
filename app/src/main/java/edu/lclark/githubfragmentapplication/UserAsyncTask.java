package edu.lclark.githubfragmentapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by Magisus on 3/3/2016.
 */
public class UserAsyncTask extends AsyncTask<String, Integer, GithubUser> {

    private static final String TAG = UserAsyncTask.class.getSimpleName();

    public interface GithubUserListener {
        public void onGithubUserRetrieved(GithubUser user);
    }

    private GithubUserListener listener;

    public UserAsyncTask(GithubUserListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Started UserAsyncTask");
    }

    @Override
    protected GithubUser doInBackground(String... params) {
        StringBuilder responseBuilder = new StringBuilder();
        GithubUser user = null;
        if (params.length == 0) {
            return null;
        }

        String userId = params[0];

        try {
            URL url = new URL("https://api.github.com/users/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled()) {
                return null;
            }
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);

                if (isCancelled()) {
                    return null;
                }
            }

            user = new Gson().fromJson(responseBuilder.toString(), GithubUser.class);

            if (isCancelled()) {
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return user;
    }

    @Override
    protected void onPostExecute(GithubUser githubUser) {
        super.onPostExecute(githubUser);
        listener.onGithubUserRetrieved(githubUser);
    }
}
