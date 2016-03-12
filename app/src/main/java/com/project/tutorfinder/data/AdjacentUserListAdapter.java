package com.project.tutorfinder.data;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import project.com.tutorfinder.R;

/**
 * A cursor adapter for listing users nearby.
 */
public final class AdjacentUserListAdapter extends CursorAdapter {

    private UserManager userManager;
    private Location userLocation;

    public AdjacentUserListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        userManager = new UserManager(context);
        userLocation = userManager.getLoggedInUserLocation();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.titleView = (TextView) v.findViewById(R.id.item_title);
        viewHolder.valueView = (TextView) v.findViewById(R.id.item_value);
        viewHolder.targetId = cursor.getInt(cursor.getColumnIndex("_id"));
        v.setTag(viewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.titleView.setText(cursor.getString(cursor.getColumnIndex("username")));
        Location destLocation = new Location("");
        destLocation.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
        destLocation.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
        viewHolder.valueView.setText(String.format("%.1f 米", userLocation.distanceTo
                (destLocation)));
    }

    static class ViewHolder {
        TextView titleView;
        TextView valueView;
        int targetId;
    }
}
