package com.project.tutorfinder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import project.com.tutorfinder.R;

/**
 * A class for managing orders.
 */
public final class OrderManager {

    private DatabaseOpenHelper databaseOpenHelper;
    private final Context context;

    public OrderManager(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
        this.context = context;
    }

    public boolean insertOrder(int senderId, int recipientId, String memo) {
        SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("sender_id", senderId);
        value.put("recipient_id", recipientId);
        if (!memo.isEmpty()) {
            value.put("memo", memo);
        }
        long id = database.insert("orders", null, value);
        return id != -1;
    }

    public OrderStatus convertToStatus(int status) {
        if (status == -1) {
            return OrderStatus.REJECTED;
        } else if (status == 1) {
            return OrderStatus.APPROVED;
        } else {
            return OrderStatus.PENDING;
        }
    }

    public String convertStatusToString(OrderStatus status) {
        if (status == OrderStatus.PENDING) {
            return context.getResources().getString(R.string.pending);
        } else if (status == OrderStatus.APPROVED) {
            return context.getResources().getString(R.string.approved);
        } else if (status == OrderStatus.REJECTED) {
            return context.getResources().getString(R.string.rejected);
        }
        return null;
    }

    public OrderStatus getOrderStatusForId(int orderId) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where _id = ?", new
                String[]{Integer.toString(orderId)});
        cursor.moveToFirst();
        return convertToStatus(cursor.getInt(cursor.getColumnIndex("status")));
    }

    public int getSenderIdForOrder(int orderId) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where _id = ?", new
                String[]{Integer.toString(orderId)});
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("sender_id"));
    }

    public int getRecipientIdForOrder(int orderId) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where _id = ?", new
                String[]{Integer.toString(orderId)});
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("recipient_id"));
    }

    public String getMemoForOrder(int orderId) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from orders where _id = ?", new
                String[]{Integer.toString(orderId)});
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("memo"));
    }

    public Cursor getCursorForSender(int id) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        return database.rawQuery("select * from orders where sender_id = ?", new String[]{Integer
                .toString(id)});
    }

    public Cursor getCursorForRecipient(int id) {
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        return database.rawQuery("select * from orders where recipient_id = ?", new
                String[]{Integer.toString(id)});
    }

    public void approveOrder(int orderId) {
        SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();
        database.execSQL("update orders set status = 1 where _id = " + orderId);
    }

    public void rejectOrder(int orderId) {
        SQLiteDatabase database = databaseOpenHelper.getWritableDatabase();
        database.execSQL("update orders set status = -1 where _id = " + orderId);
    }

    public enum OrderStatus {
        PENDING, APPROVED, REJECTED
    }
}
