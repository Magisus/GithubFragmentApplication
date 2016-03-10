package edu.lclark.githubfragmentapplication.adapters;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import edu.lclark.githubfragmentapplication.fragments.UserFragment;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by Magisus on 3/8/2016.
 */
public class FollowerTabAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<GithubUser> followers;

    public FollowerTabAdapter(Context context, List<GithubUser> followers, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.followers = followers;
    }

    @Override
    public Fragment getItem(int position) {
        return UserFragment.newInstance(followers.get(position));
    }

    @Override
    public int getCount() {
        return followers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return followers.get(position).getLogin();
    }
}
