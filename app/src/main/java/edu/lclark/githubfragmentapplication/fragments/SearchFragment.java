package edu.lclark.githubfragmentapplication.fragments;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.UserAsyncTask;
import edu.lclark.githubfragmentapplication.activities.MainActivity;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by Magisus on 3/3/2016.
 */
public class SearchFragment extends Fragment implements UserAsyncTask.GithubUserListener {

    private AsyncTask<String, Integer, GithubUser> userAsyncTask;

    @Bind(R.id.username_edit_text)
    EditText usernameInput;

    @Bind(R.id.login_button)
    Button loginButton;

    UserFragment.UserListener listener;

    @Override
    public void onGithubUserRetrieved(GithubUser user) {
        if (user == null) {
            loginButton.setEnabled(true);
            Toast.makeText(getActivity(), getActivity().getString(R.string.user_not_found_error),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        ((MainActivity) getActivity()).showNewUser(user);
    }

    public interface FindUserListener {
        public void showNewUser(GithubUser user);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        getActivity().setTitle(getActivity().getString(R.string.login_fragment_title));

        ButterKnife.bind(this, rootView);

        listener = (MainActivity) getActivity();
        usernameInput.setText("");
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        userAsyncTask.cancel(true);
    }

    @OnClick(R.id.login_button)
    public void onFindUserClick() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService
                (MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.no_internet_error),
                    Toast.LENGTH_SHORT).show();
        } else {
            userAsyncTask = new UserAsyncTask(this).execute(usernameInput.getText().toString());
            loginButton.setEnabled(false);
        }
    }


}
