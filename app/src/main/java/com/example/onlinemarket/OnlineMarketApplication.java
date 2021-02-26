package com.example.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.example.onlinemarket.event.NotificationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OnlineMarketApplication extends Application {
    public static String TAG = "OnlineMarket";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            NotificationChannel channel = new NotificationChannel(channelId, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING , priority = 1)
    public void onNotificationEventListener(NotificationEvent notificationEvent){
        String msg = "Application received the notification event";
        Log.d(TAG, msg);

        NotificationManagerCompat notificationManagerCompat=
                NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(notificationEvent.getNotificationId(),
                notificationEvent.getNotification());

    }
}
