package com.project.tutorfinder.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import project.com.tutorfinder.R;

/**
 * A fragment for the user to log in.
 */
public final class UserLoginFragment extends DialogFragment {

    public static final String TAG = "LoginDiag";

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login)
                .setView(inflater.inflate(R.layout.login_window, null))
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.register, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterUserFragment registerFragment = new RegisterUserFragment();
                        registerFragment.show(getActivity().getSupportFragmentManager(),
                                RegisterUserFragment.TAG);
                    }
                });
        return builder.create();
    }


}
