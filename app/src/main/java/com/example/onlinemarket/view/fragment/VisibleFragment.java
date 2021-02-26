package com.example.onlinemarket.view.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.event.NotificationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VisibleFragment extends Fragment {

    public static String TAG = "OnlineMarket";

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onNotificationEventListener(NotificationEvent notificationEvent) {
        String msg = "The fragment received the notification event";
        Log.d(TAG, msg);
        EventBus.getDefault().cancelEventDelivery(notificationEvent);
    }
}
