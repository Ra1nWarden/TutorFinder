package com.project.tutorfinder.ui;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;

/**
 * List fragment for handling user selection.
 */
public final class AdjacentUserListFragment extends ListFragment implements AdapterView
        .OnItemClickListener {

    @Override
    public void onResume() {
        super.onResume();
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
