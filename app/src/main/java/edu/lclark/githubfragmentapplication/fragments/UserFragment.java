package edu.lclark.githubfragmentapplication.fragments;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.FollowersAsyncTask;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.activities.MainActivity;
import edu.lclark.githubfragmentapplication.activities.TabbedFollowersActivity;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by ntille on 2/25/16.
 */
public class UserFragment extends Fragment implements FollowersAsyncTask.GithubListener {

    public static final String ARG_USER = "UserFragment.User";
    public static final String ARG_FOLLOWERS = "ARG_FOLLOWERS";
    private GithubUser mUser;

    @Bind(R.id.fragment_user_imageview)
    ImageView mImageView;
    @Bind(R.id.fragment_user_name_textview)
    TextView mNameTextView;
    @Bind(R.id.fragment_user_user_button)
    Button followerButton;

    private AsyncTask<String, Integer, ArrayList<GithubUser>> followerAsyncTask;

    @Override
    public void onGithubFollowersRetrieved(@Nullable ArrayList<GithubUser> followers) {
        Intent intent = new Intent(getActivity(), TabbedFollowersActivity.class);
        intent.putParcelableArrayListExtra(ARG_FOLLOWERS, followers);
        startActivity(intent);
    }

    public interface UserListener {
        void onUserFollowerButtonClicked(GithubUser user);
    }

    public static UserFragment newInstance(GithubUser user) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        ButterKnife.bind(this, rootView);

        mUser = getArguments().getParcelable(ARG_USER);
        assert mUser != null;

        Picasso.with(getContext()).load(mUser.getAvatar_url()).fit().centerInside().into(mImageView);

        mNameTextView.setText(mUser.getLogin());
        followerButton.setEnabled(true);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        followerButton.setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (followerAsyncTask != null && !followerAsyncTask.isCancelled()) {
            followerAsyncTask.cancel(true);
            followerAsyncTask = null;
        }
    }

    @OnClick(R.id.fragment_user_user_button)
    public void onFollowerButtonClick() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService
                (MainActivity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.no_internet_error),
                    Toast.LENGTH_SHORT).show();
        } else {
            followerAsyncTask = new FollowersAsyncTask(this).execute(mUser.getLogin());
            followerButton.setEnabled(false);
        }
    }
}
