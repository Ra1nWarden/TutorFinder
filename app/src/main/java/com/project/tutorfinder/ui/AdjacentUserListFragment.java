package com.project.tutorfinder.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.project.tutorfinder.data.AdjacentUserListAdapter.ViewHolder;

/**
 * List fragment for handling user selection.
 */
public final class AdjacentUserListFragment extends ListFragment {

    private static final String CLICKABLE_KEY = "clickable";

    public static AdjacentUserListFragment createAdjacentUserList(boolean clickable) {
        Bundle args = new Bundle();
        args.putBoolean(CLICKABLE_KEY, clickable);
        AdjacentUserListFragment f = new AdjacentUserListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        if (getArguments().getBoolean(CLICKABLE_KEY)) {
            super.onListItemClick(l, view, position, id);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder.tutor) {
                NewRequestDialogFragment newOrder = NewRequestDialogFragment.createFragmentWithId
                        (viewHolder.targetId);
                newOrder.show(getActivity().getSupportFragmentManager(), NewRequestDialogFragment
                        .DIALOG_KEY);
            }
        }
    }
}
