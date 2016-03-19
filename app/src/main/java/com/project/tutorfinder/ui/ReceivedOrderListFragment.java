package com.project.tutorfinder.ui;

import android.content.DialogInterface;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tutorfinder.data.OrderListAdapter;
import com.project.tutorfinder.data.OrderManager;
import com.project.tutorfinder.data.UserManager;

import project.com.tutorfinder.R;

/**
 * A list fragment for managing received orders.
 */
public final class ReceivedOrderListFragment extends ListFragment {

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        OrderListAdapter.ViewHolder viewHolder = (OrderListAdapter.ViewHolder) view.getTag();
        final int orderId = viewHolder.orderId;
        final OrderManager orderManager = new OrderManager(getActivity());
        UserManager userManager = new UserManager(getActivity());
        OrderManager.OrderStatus status = orderManager.getOrderStatusForId(orderId);
        String message = getActivity().getResources().getString(R.string.approve_order_message) +
                orderManager.getMemoForOrder(orderId);
        if (status == OrderManager.OrderStatus.PENDING) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.approve_order)
                    .setMessage(message)
                    .setPositiveButton(R.string.approve, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            orderManager.approveOrder(orderId);
                            Toast.makeText(getActivity(), getActivity().getResources().getString
                                    (R.string.order_approved_toast), Toast
                                    .LENGTH_SHORT).show();
                            dialog.dismiss();
                            OrderListAdapter adapter = (OrderListAdapter) getListAdapter();
                            adapter.reloadData();
                        }
                    })
                    .setNegativeButton(R.string.reject, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            orderManager.rejectOrder(orderId);
                            Toast.makeText(getActivity(), getActivity().getResources().getString
                                    (R.string.order_rejected_toast), Toast
                                    .LENGTH_SHORT).show();
                            dialog.dismiss();
                            OrderListAdapter adapter = (OrderListAdapter) getListAdapter();
                            adapter.reloadData();
                        }
                    });
            builder.create().show();
        } else if (status == OrderManager.OrderStatus.REJECTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.check_order)
                    .setMessage(R.string.order_rejected)
                    .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else if (status == OrderManager.OrderStatus.APPROVED) {
            int senderId = orderManager.getSenderIdForOrder(orderId);
            View informationView = LayoutInflater.from(getActivity()).inflate(R.layout
                    .user_information_window, null);
            TextView realNameView = (TextView) informationView.findViewById(R.id.real_name_field);
            realNameView.setText(userManager.getUserFieldForId("realname", senderId));
            TextView phoneNumberView = (TextView) informationView.findViewById(R.id
                    .phone_number_field);
            phoneNumberView.setText(userManager.getUserFieldForId("phone_number",
                    senderId));
            TextView addressView = (TextView) informationView.findViewById(R.id.address_field);
            addressView.setText(userManager.getUserFieldForId("address", senderId));
            TextView memoView = (TextView) informationView.findViewById(R.id.memo_field);
            memoView.setText(orderManager.getMemoForOrder(orderId));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.student_info)
                    .setView(informationView)
                    .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ;
                        }
                    });
            builder.create().show();
            ;
        }
    }
}
