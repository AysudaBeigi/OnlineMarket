package com.example.onlinemarket.event;

import android.app.Notification;

public class NotificationEvent {

    private int mNotificationId;
    private Notification mNotification;

    public int getNotificationId() {
        return mNotificationId;
    }

    public Notification getNotification() {
        return mNotification;
    }

    public NotificationEvent(int notificationId, Notification notification) {
        mNotificationId = notificationId;
        mNotification = notification;
    }
}
