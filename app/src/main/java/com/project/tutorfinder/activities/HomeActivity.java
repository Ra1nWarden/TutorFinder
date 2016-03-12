package com.project.tutorfinder.activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.tutorfinder.data.AdjacentUserListAdapter;
import com.project.tutorfinder.data.DatabaseOpenHelper;
import com.project.tutorfinder.data.NavigationListAdapter;
import com.project.tutorfinder.data.OrderListAdapter;
import com.project.tutorfinder.data.OrderManager;
import com.project.tutorfinder.data.ProfileListAdapter;
import com.project.tutorfinder.data.UserManager;
import com.project.tutorfinder.ui.AdjacentUserListFragment;
import com.project.tutorfinder.ui.ReceivedOrderListFragment;
import com.project.tutorfinder.ui.SentOrderListFragment;
import com.project.tutorfinder.ui.UserLoginFragment;

import project.com.tutorfinder.R;

/**
 * The home activity for the entire app.
 */
public final class HomeActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener {

    private DrawerLayout drawer;
    private ListView options;
    private UserManager userManager;
    private OrderManager orderManager;
    private TextView titleView;
    private NavigationListAdapter navigationAdapter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        options = (ListView) findViewById(R.id.left_drawer);
        options.setOnItemClickListener(this);
        userManager = new UserManager(this);
        orderManager = new OrderManager(this);
        setUpActionBar();
        ListView navigationList = (ListView) findViewById(R.id.left_drawer);
        navigationAdapter = new NavigationListAdapter(this);
        navigationList.setAdapter(navigationAdapter);
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
            loginFragment.show(getSupportFragmentManager(), UserLoginFragment.TAG);
        } else {
            goToHomeView();
        }
    }

    public void goToHomeView() {
        AdjacentUserListFragment userList = new AdjacentUserListFragment();
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        String latStr = Double.toString(userManager.getLoggedInUserLatitude());
        String lonStr = Double.toString(userManager.getLoggedInUserLongitude());
        Cursor cursor = database.rawQuery("select * from users where _id != ? order by " +
                "(latitude - ?) * " +
                "(latitude - ?) + " +
                "(longitude - ?) * (longitude - ?) asc", new String[]{Integer.toString
                (userManager.getLoggedInUserId()), latStr, latStr, lonStr,
                lonStr});
        AdjacentUserListAdapter adapter = new AdjacentUserListAdapter(this, cursor, 0);
        userList.setListAdapter(adapter);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, userList)
                .commit();
        titleView.setText((String) navigationAdapter.getItem(0));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.log_out)
                    .setMessage(R.string.confirm_log_out)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userManager.logout();
                            UserLoginFragment loginFragment = new UserLoginFragment();
                            loginFragment.show(getSupportFragmentManager(), UserLoginFragment.TAG);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (position == 3) {
            ListFragment profileList = new ListFragment();
            profileList.setListAdapter(new ProfileListAdapter(this));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, profileList)
                    .commit();
            titleView.setText((String) navigationAdapter.getItem(position));
        } else if (position == 0) {
            goToHomeView();
        } else if (position == 1) {
            SentOrderListFragment sentOrders = new SentOrderListFragment();
            OrderListAdapter adapter = new OrderListAdapter(this, orderManager.getCursorForSender
                    (userManager.getLoggedInUserId()), 0, "recipient_id");
            sentOrders.setListAdapter(adapter);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, sentOrders)
                    .commit();
            titleView.setText((String) navigationAdapter.getItem(position));
        } else {
            // position == 2
            ReceivedOrderListFragment receivedOrders = new ReceivedOrderListFragment();
            OrderListAdapter adapter = new OrderListAdapter(this, orderManager.getCursorForRecipient
                    (userManager.getLoggedInUserId()), 0, "sender_id");
            receivedOrders.setListAdapter(adapter);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, receivedOrders)
                    .commit();
            titleView.setText((String) navigationAdapter.getItem(position));
        }
        drawer.closeDrawer(options);
    }
}
