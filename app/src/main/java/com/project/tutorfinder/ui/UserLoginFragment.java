package com.project.tutorfinder.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.tutorfinder.data.UserManager;

import project.com.tutorfinder.R;

/**
 * A fragment for the user to log in.
 */
public final class UserLoginFragment extends DialogFragment {

    public static final String TAG = "LoginDiag";
    private LinearLayout forms;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        forms = (LinearLayout) inflater.inflate(R.layout.login_window, null);
        final EditText userNameField = (EditText) forms.findViewById(R.id.use_name_field);
        final EditText passwordField = (EditText) forms.findViewById(R.id.password_field);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login)
                .setView(forms)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserManager manager = new UserManager(getActivity());
                        if (manager.login(userNameField.getText().toString(), passwordField
                                .getText().toString())) {
                            toast(getActivity(), R.string.login_sucess);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(UserLoginFragment.this)
                                    .commit();
                        } else {
                            toast(getActivity(), R.string.login_failure);
                            passwordField.setText("");
                            userNameField.setSelection(0, userNameField.getText().toString()
                                    .length());
                        }
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

    private void toast(Context context, int failedTextId) {
        String failedText = context.getResources().getString(failedTextId);
        Toast.makeText(context, failedText, Toast.LENGTH_SHORT).show();
    }


}
