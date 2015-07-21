package com.despectra.githubrepoview;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mLoginLayout;
    private TextInputLayout mPasswordLayout;
    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        extractViews();
        mLoginButton.setOnClickListener(this);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if( BuildConfig.DEBUG && view.getId() != R.id.login_btn) {
            throw new AssertionError("Only login_btn is allowed to have click listener");
        }
        if(!validateFields()) {
            return;
        }
        String login = mLoginEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        //TODO perform login procedure
    }

    /**
     * @return true if login and password fields are filled correctly
     */
    private boolean validateFields() {
        boolean loginValid = !TextUtils.isEmpty(mLoginEdit.getText());
        boolean passwordValid = !TextUtils.isEmpty(mLoginEdit.getText());
        mLoginLayout.setError(!loginValid ? getString(R.string.field_empty_error) : "");
        mPasswordLayout.setError(!passwordValid ? getString(R.string.field_empty_error) : "");
        return loginValid && passwordValid;
    }
}
