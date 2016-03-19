package com.project.tutorfinder.ui;

import android.content.DialogInterface;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.tutorfinder.data.OrderListAdapter;
import com.project.tutorfinder.data.OrderManager;
import com.project.tutorfinder.data.UserManager;

import project.com.tutorfinder.R;

/**
 * Manage sent order.
 */
public final class SentOrderListFragment extends ListFragment {

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        OrderListAdapter.ViewHolder viewHolder = (OrderListAdapter.ViewHolder) view.getTag();
        int orderId = viewHolder.orderId;
        OrderManager orderManager = new OrderManager(getActivity());
        OrderManager.OrderStatus status = orderManager.getOrderStatusForId(orderId);
        if (status == OrderManager.OrderStatus.APPROVED) {
            UserManager userManager = new UserManager(getActivity());
            int recipientId = orderManager.getRecipientIdForOrder(orderId);
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View informationView = layoutInflater.inflate(R.layout.user_information_window, null);
            TextView realNameView = (TextView) informationView.findViewById(R.id.real_name_field);
            realNameView.setText(userManager.getUserFieldForId("realname", recipientId));
            TextView phoneNumberView = (TextView) informationView.findViewById(R.id
                    .phone_number_field);
            phoneNumberView.setText(userManager.getUserFieldForId("phone_number",
                    recipientId));
            TextView addressView = (TextView) informationView.findViewById(R.id.address_field);
            addressView.setText(userManager.getUserFieldForId("address", recipientId));
            TextView memoView = (TextView) informationView.findViewById(R.id.memo_field);
            memoView.setText(orderManager.getMemoForOrder(orderId));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.tutor_info)
                    .setView(informationView)
                    .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
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
        } else if (status == OrderManager.OrderStatus.PENDING) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.check_order)
                    .setMessage(R.string.order_pending)
                    .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }
}
