package com.project.tutorfinder.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
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
 * A dialog fragment for creating new user.
 */
public final class RegisterUserFragment extends DialogFragment {

    public static final String TAG = "RegisterDiag";
    private LinearLayout forms;
    private EditText latField;
    private EditText lonField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        forms = (LinearLayout) inflater.inflate(R.layout.register_window, null);
        latField = (EditText) forms.findViewById(R.id.latitude_field);
        lonField = (EditText) forms.findViewById(R.id.longitude_field);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.register)
                .setView(forms)
                .setPositiveButton(R.string.done_and_login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userName = getInputData(R.id.use_name_field);
                        if (userName.isEmpty()) {
                            toast(getActivity(), R.string.empty_username);
                            return;
                        }
                        String password1 = getInputData(R.id.password_field);
                        String password2 = getInputData(R.id.confirm_password_field);
                        if (password1.isEmpty() || password2.isEmpty()) {
                            toast(getActivity(), R.string.empty_password);
                            return;
                        }
                        if (!password1.equals(password2)) {
                            toast(getActivity(), R.string.unmatched_password);
                            return;
                        }
                        String realName = getInputData(R.id.real_name_field);
                        if (realName.isEmpty()) {
                            toast(getActivity(), R.string.empty_real_name);
                            return;
                        }
                        String phoneNumber = getInputData(R.id.phone_number_field);
                        if (phoneNumber.isEmpty()) {
                            toast(getActivity(), R.string.empty_phone_number);
                            return;
                        }
                        String address = getInputData(R.id.address_field);
                        String latString = getInputData(R.id.latitude_field);
                        String lonString = getInputData(R.id.longitude_field);
                        if (address.isEmpty() || latString.isEmpty() || lonString.isEmpty()) {
                            toast(getActivity(), R.string.empty_address);
                            return;
                        }
                        double latitude = Double.parseDouble(latString);
                        double longitude = Double.parseDouble(lonString);
                        UserManager userManager = new UserManager(getActivity());
                        userManager.registerUser(userName, password1, realName, phoneNumber,
                                address, latitude, longitude);
                        if (userManager.login(userName, password1)) {
                            toast(getActivity(), R.string.login_sucess);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .remove(RegisterUserFragment.this)
                                    .commit();
                        } else {
                            toast(getActivity(), R.string.unkown_eror);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserLoginFragment loginFragment = new UserLoginFragment();
                        loginFragment.show(getActivity().getSupportFragmentManager(),
                                UserLoginFragment.TAG);
                    }
                })
                .setCancelable(false);
        Dialog diag = builder.create();
        diag.setCanceledOnTouchOutside(false);
        return diag;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService
                (Context.LOCATION_SERVICE);
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latField.setText(String.format("%.4f", lastLocation.getLatitude()));
        lonField.setText(String.format("%.4f", lastLocation.getLongitude()));
    }

    private String getInputData(int formViewId) {
        EditText text = (EditText) forms.findViewById(formViewId);
        return text.getText().toString().trim();
    }

    private void toast(Context context, int failedTextId) {
        String failedText = context.getResources().getString(failedTextId);
        Toast.makeText(context, failedText, Toast.LENGTH_SHORT).show();
    }
}
