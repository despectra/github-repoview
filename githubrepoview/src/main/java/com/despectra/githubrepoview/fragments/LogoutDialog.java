package com.despectra.githubrepoview.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.despectra.githubrepoview.R;

/**
 * Very simple logout confirmation dialog
 */
public class LogoutDialog extends DialogFragment {

    public static final String TAG = "LogoutDialog";
    private Callback mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_confirm))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mCallback != null) {
                            mCallback.onLogoutConfirmed();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null);
        return builder.create();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onLogoutConfirmed();
    }
}
