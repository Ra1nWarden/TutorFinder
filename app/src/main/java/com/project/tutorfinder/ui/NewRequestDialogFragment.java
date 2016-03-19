package com.project.tutorfinder.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tutorfinder.data.OrderManager;
import com.project.tutorfinder.data.UserManager;

import project.com.tutorfinder.R;

/**
 * A dialog for sending new request.
 */
public final class NewRequestDialogFragment extends DialogFragment {

    public static final String DIALOG_KEY = "NewRequestDiag";
    public static final String USER_ID_KEY = "userId";

    public static NewRequestDialogFragment createFragmentWithId(int userId) {
        NewRequestDialogFragment f = new NewRequestDialogFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID_KEY, userId);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.new_request_window, null);
        final int recipientId = getArguments().getInt(USER_ID_KEY);
        final UserManager userManager = new UserManager(getActivity());
        final EditText memoView = (EditText) v.findViewById(R.id.memo_field);
        final EditText recipientView = (EditText) v.findViewById(R.id.recipient_field);
        final EditText subjectView = (EditText) v.findViewById(R.id.subject_field);
        subjectView.setText(userManager.getUserFieldForId("subject", recipientId));
        recipientView.setText(userManager.getUsernameForId(recipientId));

        final EditText tutorNameView = (EditText) v.findViewById(R.id.tutor_name_field);
        tutorNameView.setText(userManager.getUserFieldForId("realname", recipientId));
        final EditText tutorAddressView = (EditText) v.findViewById(R.id.tutor_address_field);
        tutorAddressView.setText(userManager.getUserFieldForId("address", recipientId));
        final EditText tutorPhoneView = (EditText) v.findViewById(R.id.tutor_phone_field);
        tutorPhoneView.setText(userManager.getUserFieldForId("phone_number", recipientId));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_request)
                .setView(v)
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderManager orderManager = new OrderManager(getActivity());
                        int senderId = userManager.getLoggedInUserId();
                        String content = memoView.getText().toString();
                        if (orderManager.insertOrder(senderId, recipientId, content)) {
                            Toast.makeText(getActivity(), getResources().getString(R.string
                                    .send_success), Toast
                                    .LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string
                                    .send_failure), Toast
                                    .LENGTH_SHORT).show();
                        }
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .remove(NewRequestDialogFragment.this)
                                .commit();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .remove(NewRequestDialogFragment.this)
                                .commit();
                    }
                })
                .setCancelable(false);
        Dialog diag = builder.create();
        diag.setCanceledOnTouchOutside(false);
        return diag;
    }
}
