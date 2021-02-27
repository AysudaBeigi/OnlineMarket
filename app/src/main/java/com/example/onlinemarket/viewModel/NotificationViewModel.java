package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;

import com.example.onlinemarket.utils.OnlineMarketPreferences;
import com.example.onlinemarket.work.PollWorker;

public class NotificationViewModel extends AndroidViewModel {
    private OnlineMarketPreferences mOnlineMarketPreferences;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        mOnlineMarketPreferences = OnlineMarketPreferences.getInstance(application);
    }

    public int getNotificationTime() {
        return mOnlineMarketPreferences.getNotificationTime();
    }

    public void setNotificationTime(int notificationTime) {
        mOnlineMarketPreferences.setNotificationTime(notificationTime);
    }

    public boolean isTaskScheduled() {
        return PollWorker.
                isWorkEnqueued(getApplication());
    }

    public void togglePolling(LifecycleOwner owner) {
        //is alarm set
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        int time = mOnlineMarketPreferences.getNotificationTime();
        PollWorker.enqueueWork(getApplication(), !isOn, time,owner);
    }


}
