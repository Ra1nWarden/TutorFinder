package com.project.tutorfinder.ui;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;

import com.project.tutorfinder.data.AdjacentUserListAdapter.ViewHolder;

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
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        NewRequestDialogFragment newOrder = NewRequestDialogFragment.createFragmentWithId
                (viewHolder.targetId);
        newOrder.show(getActivity().getSupportFragmentManager(), NewRequestDialogFragment
                .DIALOG_KEY);
    }
}
