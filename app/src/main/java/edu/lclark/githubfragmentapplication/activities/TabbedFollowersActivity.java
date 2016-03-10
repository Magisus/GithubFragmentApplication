package edu.lclark.githubfragmentapplication.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.adapters.FollowerTabAdapter;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

public class TabbedFollowersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_followers);

        ArrayList<GithubUser> followers = getIntent()
                .getParcelableArrayListExtra(UserFragment.ARG_FOLLOWERS);

        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_followers_viewpager);
        FollowerTabAdapter adapter = new FollowerTabAdapter(this, followers, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_followers_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_tabbed_followers_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.tabbed_followers_activity_title));

    }

}
