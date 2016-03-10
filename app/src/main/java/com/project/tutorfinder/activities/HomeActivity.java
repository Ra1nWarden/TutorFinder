package com.project.tutorfinder.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import project.com.tutorfinder.R;

/**
 * The home activity for the entire app.
 */
public final class HomeActivity extends AppCompatActivity {

    private TextView titleView;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.home);
        setUpActionBar();
        ListView navigationList = (ListView) findViewById(R.id.left_drawer);
        String[] items = getResources().getStringArray(R.array.navigation_items);
        navigationList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, items));
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        View actionBarView = inflater.inflate(R.layout.home_action_bar, null);
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

}
