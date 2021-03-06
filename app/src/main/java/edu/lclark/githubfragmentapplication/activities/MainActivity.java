package edu.lclark.githubfragmentapplication.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.SearchFragment;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.fragments.MainActivityFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

public class MainActivity extends AppCompatActivity implements
        MainActivityFragment.FollowerSelectedListener,
        UserFragment.UserListener,
        SearchFragment.FindUserListener {

    @Bind(R.id.activity_main_framelayout)
    FrameLayout mFrameLayout;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginFragment();
            }
        });

        showLoginFragment();

    }

    private void showLoginFragment() {
        fab.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, new SearchFragment());
        transaction.commit();
    }

    @Override
    public void onFollowerSelected(GithubUser follower) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, UserFragment.newInstance(follower));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onUserFollowerButtonClicked(GithubUser user) {
        fab.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, MainActivityFragment.newInstance(user));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showNewUser(GithubUser user) {
        fab.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        setTitle(user.getLogin());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_framelayout, UserFragment.newInstance(user));
        transaction.commit();
    }
}
