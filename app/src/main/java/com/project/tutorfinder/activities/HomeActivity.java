package com.project.tutorfinder.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.tutorfinder.data.NavigationListAdapter;
import com.project.tutorfinder.data.UserManager;
import com.project.tutorfinder.ui.UserLoginFragment;

import project.com.tutorfinder.R;

/**
 * The home activity for the entire app.
 */
public final class HomeActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener {

    private final String LOGIN_TAG = "logInDialog";
    private DrawerLayout drawer;
    private ListView options;
    private UserManager userManager;
    private TextView titleView;
    private NavigationListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        options = (ListView) findViewById(R.id.left_drawer);
        options.setOnItemClickListener(this);
        userManager = new UserManager(this);
        setUpActionBar();
        ListView navigationList = (ListView) findViewById(R.id.left_drawer);
        adapter = new NavigationListAdapter(this);
        navigationList.setAdapter(adapter);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        View actionBarView = inflater.inflate(R.layout.home_action_bar, null);
        titleView = (TextView) actionBarView.findViewById(R.id.app_title_text);
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    public void toggleDrawer(View v) {
        if (drawer.isDrawerOpen(options)) {
            drawer.closeDrawer(options);
        } else {
            drawer.openDrawer(options);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!userManager.userLoggedIn()) {
            UserLoginFragment loginFragment = new UserLoginFragment();
            loginFragment.show(getSupportFragmentManager(), LOGIN_TAG);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        titleView.setText((String) adapter.getItem(position));
        drawer.closeDrawer(options);
    }
}
