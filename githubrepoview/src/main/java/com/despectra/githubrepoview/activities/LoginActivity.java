package com.despectra.githubrepoview.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.despectra.githubrepoview.App;
import com.despectra.githubrepoview.BuildConfig;
import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.loaders.network.LoginLoader;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.net.Error;
import com.pnikosis.materialishprogress.ProgressWheel;

import io.realm.Realm;

/**
 * Simple login activity with 2 fields and 1 button
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<User> {

    /**
     * Keys for saving activity state
     */
    private static final String LOGIN_CLICKED = "loginClicked";

    /**
     * Loader related constants
     */
    private static final int LOADER_ID = 1;
    private static final String LOADER_PARAM_LOGIN = "login";
    private static final String LOADER_PARAM_PASSWORD = "password";

    private TextInputLayout mLoginLayout;
    private TextInputLayout mPasswordLayout;
    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private Button mLoginButton;
    private ProgressWheel mProgressWheel;

    /**
     * Field indicating whether Login button was clicked and logging process was started
     */
    private boolean mLoginClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(LoginInfo.getLoggedUser(this) != null) {
            //if we are logged in, jump to main activity
            launchFriendsActivity();
            return;
        }
        setContentView(R.layout.activity_login);
        extractViews();
        restoreState(savedInstanceState);
        mLoginButton.setOnClickListener(this);
    }

    /**
     * Restores activity state if the configuration change happened
     * Reuses loaders if needed
     * @param savedInstanceState bundle with activity state
     */
    private void restoreState(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            mLoginClicked = false;
            return;
        }
        mLoginClicked = savedInstanceState.getBoolean(LOGIN_CLICKED, false);
        mProgressWheel.setVisibility(mLoginClicked ? View.VISIBLE : View.GONE);
        if(mLoginClicked) {
            runLoginLoader();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOGIN_CLICKED, mLoginClicked);
    }

    /**
     * Extracts views from hierarchy and assigns it to view fields
     */
    private void extractViews() {
        mLoginLayout = (TextInputLayout) findViewById(R.id.login_edit_layout);
        mLoginEdit = (EditText) findViewById(R.id.login_edit);
        mPasswordLayout = (TextInputLayout) findViewById(R.id.password_edit_layout);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
    }

    /**
     * Login button click listener
     * @param view view clicked on
     */
    @Override
    public void onClick(View view) {
        if( BuildConfig.DEBUG && view.getId() != R.id.login_btn) {
            throw new AssertionError("Only login_btn is allowed to have click listener");
        }
        if(mLoginClicked || !validateFields()) {
            return;
        }
        runLoginLoader();
        mLoginClicked = true;
        mProgressWheel.setVisibility(View.VISIBLE);
    }

    private void runLoginLoader() {
        String login = mLoginEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        Bundle loaderParams = new Bundle();
        loaderParams.putString(LOADER_PARAM_LOGIN, login);
        loaderParams.putString(LOADER_PARAM_PASSWORD, password);
        getLoaderManager().initLoader(LOADER_ID, loaderParams, this);
    }

    /**
     * @return true if login and password fields are filled correctly
     */
    private boolean validateFields() {
        boolean loginValid = !TextUtils.isEmpty(mLoginEdit.getText());
        boolean passwordValid = !TextUtils.isEmpty(mPasswordEdit.getText());
        mLoginLayout.setError(!loginValid ? getString(R.string.field_empty_error) : "");
        mPasswordLayout.setError(!passwordValid ? getString(R.string.field_empty_error) : "");
        return loginValid && passwordValid;
    }

    private void prepareDatabase() {
        App app = (App) getApplication();
        Realm.deleteRealm(app.getDefaultRealmConfiguration());
    }

    /**
     * Launches main activity after successful login
     */
    private void launchFriendsActivity() {
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Creates new login loader with login and password provided
     * @param id loader ID
     * @param bundle loader parameters
     * @return login loader
     */
    @Override
    public Loader<User> onCreateLoader(int id, Bundle bundle) {
        if(BuildConfig.DEBUG && bundle == null) {
            throw new AssertionError("Loader params should not be empty");
        }
        return new LoginLoader(this, bundle.getString(LOADER_PARAM_LOGIN), bundle.getString(LOADER_PARAM_PASSWORD));
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User user) {
        LoginLoader loginLoader = (LoginLoader) loader;
        if (loginLoader.loadingSucceeded()) {
            prepareDatabase();
            launchFriendsActivity();
        } else {
            Error error = loginLoader.getError();
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            mLoginClicked = false;
            mProgressWheel.setVisibility(View.GONE);
        }
        getLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {
    }

}
