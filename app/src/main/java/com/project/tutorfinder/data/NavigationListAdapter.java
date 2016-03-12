package com.project.tutorfinder.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import project.com.tutorfinder.R;

/**
 * Adapter for navigation list.
 */
public final class NavigationListAdapter extends BaseAdapter {

    private String[] navigationalItems;
    private final Context context;

    public NavigationListAdapter(Context context) {
        this.context = context;
        navigationalItems = context.getResources().getStringArray(R.array.navigation_items);
    }

    @Override
    public int getCount() {
        return navigationalItems.length;
    }

    @Override
    public Object getItem(int position) {
        return navigationalItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.navigation_label);
        textView.setText(navigationalItems[position]);
        return view;
    }
}
