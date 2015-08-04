package com.despectra.githubrepoview.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.fragments.FriendsFragment;
import com.despectra.githubrepoview.fragments.LogoutDialog;

/**
 * Activity that contains different items list fragments
 */
public class MainActivity extends AppCompatActivity {

    private LogoutDialog.Callback mLogoutDialogCallback = new LogoutDialog.Callback() {
        @Override
        public void onLogoutConfirmed() {
            performLogout();
        }
    };
    private SearchView.OnQueryTextListener mSearchViewListener;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupAppBar();
        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new FriendsFragment(), FriendsFragment.TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        } else {
            LogoutDialog dialog =  (LogoutDialog) getFragmentManager().findFragmentByTag(LogoutDialog.TAG);
            if(dialog != null) {
                dialog.setCallback(mLogoutDialogCallback);
            }
        }
    }

    public void setupAppBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
    }

    public void setSearchViewListener(SearchView.OnQueryTextListener listener) {
        mSearchViewListener = listener;
        if(mSearchView != null) {
            mSearchView.setOnQueryTextListener(mSearchViewListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(mSearchViewListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                showLogoutDialog();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    /**
     * Shows logout confirmation dialog
     */
    private void showLogoutDialog() {
        LogoutDialog dialog = (LogoutDialog) getFragmentManager().findFragmentByTag(LogoutDialog.TAG);
        if(dialog == null) {
            dialog = new LogoutDialog();
        }
        dialog.setCallback(mLogoutDialogCallback);
        dialog.show(getFragmentManager(), LogoutDialog.TAG);
    }

    /**
     * Logout action implementation
     * Remove all user credentials from shared preferences, switch to LoginActivity
     */
    private void performLogout() {
        LoginInfo.clearLoggedUser(this);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
