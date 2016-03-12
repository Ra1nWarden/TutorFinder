package com.project.tutorfinder.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.com.tutorfinder.R;

/**
 * An adapter for showing all orders.
 */
public final class OrderListAdapter extends CursorAdapter {

    private String columnNameForDisplay;
    private LayoutInflater layoutInflater;
    private OrderManager orderManager;
    private Context context;

    public OrderListAdapter(Context context, Cursor c, int flags, String columnNameForDisplay) {
        super(context, c, flags);
        this.columnNameForDisplay = columnNameForDisplay;
        layoutInflater = LayoutInflater.from(context);
        orderManager = new OrderManager(context);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.usernameField = (TextView) view.findViewById(R.id.item_title);
        viewHolder.statusField = (TextView) view.findViewById(R.id.item_value);
        viewHolder.orderId = cursor.getInt(cursor.getColumnIndex("_id"));
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.usernameField.setText(cursor.getString(cursor.getColumnIndex
                (columnNameForDisplay)));
        viewHolder.orderId = cursor.getInt(cursor.getColumnIndex("_id"));
        OrderManager.OrderStatus orderStatus = orderManager.getOrderStatusForId(viewHolder.orderId);
        viewHolder.statusField.setText(orderManager.convertStatusToString(orderStatus));
        if (orderStatus == OrderManager.OrderStatus.APPROVED) {
            viewHolder.statusField.setTextColor(context.getResources().getColor(android.R.color
                    .holo_green_dark));
        } else if (orderStatus == OrderManager.OrderStatus.PENDING) {
            viewHolder.statusField.setTextColor(context.getResources().getColor(android.R.color
                    .holo_orange_dark));
        } else {
            viewHolder.statusField.setTextColor(context.getResources().getColor(android.R.color
                    .holo_red_dark));
        }
    }

    static class ViewHolder {
        TextView usernameField;
        TextView statusField;
        int orderId;
    }
}
