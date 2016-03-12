package com.project.tutorfinder.ui;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.project.tutorfinder.data.AdjacentUserListAdapter.ViewHolder;

/**
 * List fragment for handling user selection.
 */
public final class AdjacentUserListFragment extends ListFragment {


    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        NewRequestDialogFragment newOrder = NewRequestDialogFragment.createFragmentWithId
                (viewHolder.targetId);
        newOrder.show(getActivity().getSupportFragmentManager(), NewRequestDialogFragment
                .DIALOG_KEY);
    }
}
